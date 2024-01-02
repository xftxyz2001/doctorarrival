package com.xftxyz.doctorarrival.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件地址
     */
    String upload(MultipartFile file);

    String getAdminPath();
}
