package com.xftxyz.doctorarrival.oss.service;

import com.aliyun.oss.model.ListObjectsV2Request;
import com.xftxyz.doctorarrival.vo.oss.OSSObjectSummaryVO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    List<OSSObjectSummaryVO> list(ListObjectsV2Request listObjectsV2Request);
}
