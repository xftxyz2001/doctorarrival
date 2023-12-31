package com.xftxyz.doctorarrival.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xftxyz.doctorarrival.common.exception.oss.FileUploadException;
import com.xftxyz.doctorarrival.common.helper.DateTimeHelper;
import com.xftxyz.doctorarrival.common.helper.FileHelper;
import com.xftxyz.doctorarrival.oss.autoconfigure.OssProperties;
import com.xftxyz.doctorarrival.oss.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
        } catch (IOException | OSSException | ClientException e) {
            throw new FileUploadException();
        }
    }

}
