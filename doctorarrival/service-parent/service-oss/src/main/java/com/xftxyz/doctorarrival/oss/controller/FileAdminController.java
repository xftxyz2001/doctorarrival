package com.xftxyz.doctorarrival.oss.controller;

import com.xftxyz.doctorarrival.oss.service.FileService;
import com.xftxyz.doctorarrival.vo.oss.ListObjectsResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "文件管理")
@RequestMapping("/admin/oss/file")
public class FileAdminController {

    private final FileService fileService;

    @Operation(summary = "获取OSS管理路径")
    @GetMapping("/path")
    public String path() {
        return fileService.getAdminPath();
    }

    @Operation(summary = "上传文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @Operation(summary = "批量上传文件")
    @PostMapping(value = "/upload/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> uploadBatch(@RequestPart("files") @NotEmpty MultipartFile[] files) {
        return fileService.uploadBatch(files);
    }

    @Operation(summary = "下载文件")
    @GetMapping(value = "/download/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public ResponseEntity<Resource> download(@PathVariable("yyyy") @NotBlank String yyyy,
                                             @PathVariable("MM") @NotBlank String MM,
                                             @PathVariable("dd") @NotBlank String dd,
                                             @PathVariable("uuidFileName") @NotBlank String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.download(fileUrl);
    }

    @Operation(summary = "批量下载文件")
    @PostMapping(value = "/download/batch")
    public ResponseEntity<Resource> downloadBatch(@RequestBody @NotEmpty List<String> fileUrls) {
        return fileService.downloadBatch(fileUrls);
    }

    @Operation(summary = "删除文件")
    @DeleteMapping(value = "/delete/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public Boolean delete(@PathVariable("yyyy") @NotBlank String yyyy,
                          @PathVariable("MM") @NotBlank String MM,
                          @PathVariable("dd") @NotBlank String dd,
                          @PathVariable("uuidFileName") @NotBlank String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.delete(fileUrl);
    }

    @Operation(summary = "批量删除文件")
    @DeleteMapping(value = "/delete/batch")
    public Boolean deleteBatch(@RequestBody @NotEmpty List<String> fileUrls) {
        return fileService.deleteBatch(fileUrls);
    }

    @Operation(summary = "获取文件列表")
    @GetMapping("/list")
    public ListObjectsResultVO list(
            @RequestParam(value = "continuationToken", required = false) String continuationToken,
            @RequestParam(value = "maxKeys", required = false, defaultValue = "20") Integer maxKeys) {
        return fileService.list(continuationToken, maxKeys);
    }

}
