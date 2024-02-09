package com.xftxyz.doctorarrival.oss.service.impl;

import com.xftxyz.doctorarrival.exception.BusinessException;
import com.xftxyz.doctorarrival.helper.DateTimeHelper;
import com.xftxyz.doctorarrival.helper.FileHelper;
import com.xftxyz.doctorarrival.oss.service.FileService;
import com.xftxyz.doctorarrival.result.ResultEnum;
import com.xftxyz.doctorarrival.vo.oss.ListObjectsResultVO;
import com.xftxyz.doctorarrival.vo.oss.OSSObjectSummaryVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// @Service
@RequiredArgsConstructor
public class FileServiceLocalImpl implements FileService {

    private final String localBasePath = "upload/";

    // 文件路径格式
    private static final String FILE_PATH_FORMAT = "yyyy/MM/dd/";

    @Override
    public String upload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String uuidFileName = FileHelper.uuidFileName(originalFilename);
            String fullFileName = DateTimeHelper.getCurrentTime(FILE_PATH_FORMAT) + uuidFileName;

            file.transferTo(new File(localBasePath + fullFileName));
            return fullFileName;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    public String getAdminPath() {
        throw new BusinessException(ResultEnum.OPERATION_NOT_SUPPORTED);
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
            File file = new File(localBasePath + fileUrl);
            String filename = fileUrl.replace("/", "-");

            return ResponseEntity.ok()
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(new FileSystemResource(file));
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
                File file = new File(localBasePath + fileUrl);
                // ZipEntry zipEntry = new ZipEntry(object.getKey().replace("/", "-"));
                ZipEntry zipEntry = new ZipEntry(fileUrl);
                zos.putNextEntry(zipEntry);

                Files.copy(file.toPath(), zos);
                zos.closeEntry();
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
            File file = new File(localBasePath + fileUrl);
            return file.delete();
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
        int skip = 0;
        try {
            skip = Integer.parseInt(continuationToken);
        } catch (Exception ignored) {
        }
        try {
            File baseDir = new File(localBasePath);
            File[] allFiles = baseDir.listFiles();

            ListObjectsResultVO listObjectsResultVO = new ListObjectsResultVO();
            if (ObjectUtils.isEmpty(allFiles)) {
                listObjectsResultVO.setObjectSummaries(new ArrayList<>());
                listObjectsResultVO.setKeyCount(0);
                listObjectsResultVO.setNextContinuationToken("0");
                return listObjectsResultVO;
            }

            List<OSSObjectSummaryVO> summaries = Arrays.stream(allFiles).sorted(Comparator.comparing(File::lastModified)).skip(skip).limit(maxKeys).map(file -> {
                OSSObjectSummaryVO ossObjectSummaryVO = new OSSObjectSummaryVO();
                ossObjectSummaryVO.setName(file.getName());
                ossObjectSummaryVO.setSize(file.length());
                ossObjectSummaryVO.setLastModified(new Date(file.lastModified()));
                ossObjectSummaryVO.setType(file.isDirectory() ? "dir" : "file");
                return ossObjectSummaryVO;
            }).toList();
            listObjectsResultVO.setObjectSummaries(summaries);
            listObjectsResultVO.setKeyCount(summaries.size());
            listObjectsResultVO.setNextContinuationToken(String.valueOf(skip + summaries.size()));
            return listObjectsResultVO;
        } catch (Exception e) {
            throw new BusinessException(ResultEnum.FILE_LIST_FAILED);
        }
    }

    @Override
    public ResponseEntity<byte[]> preview(String fileUrl) {
        try {
            File file = new File(localBasePath + fileUrl);
            // 获取文件后缀名以确定响应头
            String suffix = FileHelper.getSuffix(fileUrl);
            byte[] imageData = IOUtils.toByteArray(file.toURI());
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
