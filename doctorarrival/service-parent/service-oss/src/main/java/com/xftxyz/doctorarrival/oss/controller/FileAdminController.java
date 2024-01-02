package com.xftxyz.doctorarrival.oss.controller;

import com.aliyun.oss.model.ListObjectsV2Request;
import com.xftxyz.doctorarrival.oss.service.FileService;
import com.xftxyz.doctorarrival.vo.oss.OSSObjectSummaryVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
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

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件地址
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    /**
     * 批量上传文件
     *
     * @param files 文件数组
     * @return 文件地址数组
     */
    @PostMapping(value = "/upload/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> uploadBatch(@RequestPart("files") @NotEmpty MultipartFile[] files) {
        return fileService.uploadBatch(files);
    }

    /**
     * 下载文件
     *
     * @param yyyy
     * @param MM
     * @param dd
     * @param uuidFileName
     * @return 文件
     */
    @GetMapping(value = "/download/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public ResponseEntity<Resource> download(@PathVariable("yyyy") @NotBlank String yyyy,
                                             @PathVariable("MM") @NotBlank String MM,
                                             @PathVariable("dd") @NotBlank String dd,
                                             @PathVariable("uuidFileName") @NotBlank String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.download(fileUrl);
    }

    /**
     * 批量下载文件
     *
     * @param fileUrls 文件地址数组
     * @return 文件
     */
    @GetMapping(value = "/download/batch")
    public ResponseEntity<Resource> downloadBatch(@RequestBody @NotEmpty List<String> fileUrls) {
        return fileService.downloadBatch(fileUrls);
    }

    /**
     * 删除文件
     *
     * @param yyyy
     * @param MM
     * @param dd
     * @param uuidFileName
     * @return 是否成功
     */
    @DeleteMapping(value = "/delete/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public Boolean delete(@PathVariable("yyyy") @NotBlank String yyyy,
                          @PathVariable("MM") @NotBlank String MM,
                          @PathVariable("dd") @NotBlank String dd,
                          @PathVariable("uuidFileName") @NotBlank String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.delete(fileUrl);
    }

    /**
     * 批量删除文件
     *
     * @param fileUrls 文件地址数组
     * @return 是否成功
     */
    @DeleteMapping(value = "/delete/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<String> fileUrls) {
        return fileService.deleteBatch(fileUrls);
    }

    /**
     * 查看文件列表
     *
     * @param listObjectsV2Request 查询条件
     * @return 文件地址数组
     */
    @GetMapping("/list")
    public List<OSSObjectSummaryVO> list(@RequestBody ListObjectsV2Request listObjectsV2Request) {
        return fileService.list(listObjectsV2Request);
    }

}
