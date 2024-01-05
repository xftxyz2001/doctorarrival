package com.xftxyz.doctorarrival.oss.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.xftxyz.doctorarrival.vo.oss.ListObjectsResultVO;

public interface FileService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件地址
     */
    String upload(MultipartFile file);

    String getAdminPath();

    List<String> uploadBatch(MultipartFile[] files);

    ResponseEntity<Resource> download(String fileUrl);

    ResponseEntity<Resource> downloadBatch(List<String> fileUrls);

    Boolean delete(String fileUrl);

    Boolean deleteBatch(List<String> fileUrls);

    ListObjectsResultVO list(String continuationToken, Integer maxKeys);
}
