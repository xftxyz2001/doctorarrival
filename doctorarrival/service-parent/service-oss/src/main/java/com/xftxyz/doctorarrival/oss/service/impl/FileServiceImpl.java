package com.xftxyz.doctorarrival.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsV2Request;
import com.aliyun.oss.model.ListObjectsV2Result;
import com.aliyun.oss.model.OSSObject;
import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.helper.DateTimeHelper;
import com.xftxyz.doctorarrival.helper.FileHelper;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.oss.autoconfigure.OssProperties;
import com.xftxyz.doctorarrival.oss.service.FileService;
import com.xftxyz.doctorarrival.vo.oss.ListObjectsResultVO;
import com.xftxyz.doctorarrival.vo.oss.OSSObjectSummaryVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final OSS ossClient;

    private final OssProperties ossProperties;

    // 文件路径格式
    private static final String FILE_PATH_FORMAT = "yyyy/MM/dd/";

    @Override
    public String upload(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();

            String originalFilename = file.getOriginalFilename();
            String uuidFileName = FileHelper.uuidFileName(originalFilename);
            String fullFileName = DateTimeHelper.getCurrentTime(FILE_PATH_FORMAT) + uuidFileName;

            ossClient.putObject(ossProperties.getBucketName(), fullFileName, inputStream);
            return fullFileName;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    public String getAdminPath() {
        return "https://oss.console.aliyun.com/bucket/" + ossProperties.getEndpoint().split("\\.")[0] + "/" + ossProperties.getBucketName() + "/object";
    }

    @Override
    public List<String> uploadBatch(MultipartFile[] files) {
        if (ObjectUtils.isEmpty(files)) {
            return new ArrayList<>();
        }
        ArrayList<String> fileUrls = new ArrayList<>();
        Arrays.stream(files).forEach(multipartFile -> fileUrls.add(upload(multipartFile)));
        return fileUrls;
    }

    @Override
    public ResponseEntity<Resource> download(String fileUrl) {
        try {
            OSSObject object = ossClient.getObject(ossProperties.getBucketName(), fileUrl);
            String filename = object.getKey().replace("/", "-");
            return ResponseEntity.ok()
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(new InputStreamResource(object.getObjectContent()));
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FILE_DOWNLOAD_FAILED);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadBatch(List<String> fileUrls) {
        if (ObjectUtils.isEmpty(fileUrls)) {
            return null;
        }
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ZipOutputStream zos = new ZipOutputStream(baos)
        ) {
            for (String fileUrl : fileUrls) {
                OSSObject object = ossClient.getObject(ossProperties.getBucketName(), fileUrl);
                // ZipEntry zipEntry = new ZipEntry(object.getKey().replace("/", "-"));
                ZipEntry zipEntry = new ZipEntry(object.getKey());
                zos.putNextEntry(zipEntry);

                IOUtils.copy(object.getObjectContent(), zos);

                zos.closeEntry();
                object.getObjectContent().close(); // 关闭OSSObject的输入流
            }
            zos.finish();
            ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"batch-download.zip\"")
                    .body(resource);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FILE_DOWNLOAD_FAILED);
        }
    }

    @Override
    public Boolean delete(String fileUrl) {
        try {
            ossClient.deleteObject(ossProperties.getBucketName(), fileUrl);
            return true;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FILE_DELETE_FAILED);
        }
    }

    @Override
    public Boolean deleteBatch(List<String> fileUrls) {
        if (ObjectUtils.isEmpty(fileUrls)) {
            return true;
        }
        fileUrls.forEach(this::delete);
        return true;
    }

    @Override
    public ListObjectsResultVO list(String continuationToken, Integer maxKeys) {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request();
        listObjectsV2Request.setBucketName(ossProperties.getBucketName());
        listObjectsV2Request.setContinuationToken(continuationToken);
        listObjectsV2Request.setMaxKeys(maxKeys);
        try {
            ListObjectsV2Result listObjectsV2Result = ossClient.listObjectsV2(listObjectsV2Request);

            ListObjectsResultVO listObjectsResultVO = new ListObjectsResultVO();
            listObjectsResultVO.setObjectSummaries(listObjectsV2Result.getObjectSummaries().stream().map(objectSummary -> {
                OSSObjectSummaryVO ossObjectSummaryVO = new OSSObjectSummaryVO();
                ossObjectSummaryVO.setName(objectSummary.getKey());
                ossObjectSummaryVO.setSize(objectSummary.getSize());
                ossObjectSummaryVO.setLastModified(objectSummary.getLastModified());
                ossObjectSummaryVO.setType(objectSummary.getType());
                return ossObjectSummaryVO;
            }).toList());
            listObjectsResultVO.setKeyCount(listObjectsV2Result.getKeyCount());
            listObjectsResultVO.setNextContinuationToken(listObjectsV2Result.getNextContinuationToken());
            return listObjectsResultVO;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FILE_LIST_FAILED);
        }
    }

    @Override
    public ResponseEntity<byte[]> preview(String fileUrl) {
        try {
            OSSObject object = ossClient.getObject(ossProperties.getBucketName(), fileUrl);
            // 获取文件后缀名以确定响应头
            String suffix = FileHelper.getSuffix(object.getKey());
            byte[] imageData = IOUtils.toByteArray(object.getObjectContent());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, getContentType(suffix))
                    .body(imageData);
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FILE_DOWNLOAD_FAILED);
        }
    }

    private String getContentType(String suffix) {
        return switch (suffix) {
            case ".jpg", ".jpeg" -> MediaType.IMAGE_JPEG_VALUE;
            case ".png" -> MediaType.IMAGE_PNG_VALUE;
            case ".gif" -> MediaType.IMAGE_GIF_VALUE;
            default -> MediaType.APPLICATION_OCTET_STREAM_VALUE;
        };
    }

}
