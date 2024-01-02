package com.xftxyz.doctorarrival.oss.controller;

import com.xftxyz.doctorarrival.oss.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/oss/file")
public class FileAdminController {

    private final FileService fileService;

    /**
     * 获取管理路径
     */
    @GetMapping("/path")
    public String path() {
        return fileService.getAdminPath();
    }

    // 以下接口未来可能会提供实现

    /**
     * 批量上传文件
     *
     * @param files 文件数组
     * @return 文件地址数组
     */
    // @PostMapping(value = "/upload/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public List<String> uploadBatch(@RequestPart("files") MultipartFile[] files) {
    //     return fileService.uploadBatch(files);
    // }

    /**
     * 下载文件
     *
     * @param fileUrl 文件地址
     * @return 文件
     */
    // @PostMapping(value = "/download/{fileUrl}")
    // public ResponseEntity<Resource> download(@PathVariable("fileUrl") String fileUrl) {
    //     Resource resource = fileService.download(fileUrl);
    //     return ResponseEntity.ok()
    //             .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
    //             .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
    //             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    //             .body(resource);
    // }

    /**
     * 批量下载文件
     *
     * @param fileUrls 文件地址数组
     * @return 文件
     */
    // @PostMapping(value = "/download/batch")
    // public ResponseEntity<Resource> downloadBatch(RequestBody List<String>fileUrls) {
    //     Resource resource = fileService.downloadBatch(fileUrls);
    //     return ResponseEntity.ok()
    //             .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
    //             .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
    //             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    //             .body(resource);
    // }

    /**
     * 删除文件
     *
     * @param fileUrl 文件地址
     * @return 是否成功
     */
    // @PostMapping(value = "/delete/{fileUrl}")
    // public Boolean delete(@PathVariable("fileUrl") String fileUrl) {
    //     return fileService.delete(fileUrl);
    // }

    /**
     * 批量删除文件
     *
     * @param fileUrls 文件地址数组
     * @return 是否成功
     */
    // @PostMapping(value = "/delete/batch")
    // public Boolean deleteBatch(RequestBody List<String>fileUrls) {
    //     return fileService.deleteBatch(fileUrls);
    // }

    /**
     * 查看文件列表
     *
     * @param listObjectsV2Request 查询条件
     * @return 文件地址数组
     */
    // @PostMapping("/list")
    // public List<OSSObjectSummaryVO> list(@RequestBody ListObjectsV2Request listObjectsV2Request) {
    //     return fileService.list(listObjectsV2Request);
    // }

}
