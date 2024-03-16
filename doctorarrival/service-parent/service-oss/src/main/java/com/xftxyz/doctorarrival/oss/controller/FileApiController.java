package com.xftxyz.doctorarrival.oss.controller;

import com.xftxyz.doctorarrival.oss.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "文件相关")
@RequestMapping("/api/oss/file")
public class FileApiController {

    private final FileService fileService;

    @Operation(summary = "上传文件",
               description = "此接口接收一个HTTP POST请求，请求体类型为Multipart/form-data，其中包含一个名为'file'的非空文件部分。接口负责处理上传过程并将文件保存至服务器，成功上传后返回文件在系统内部的存储路径或其他唯一标识符。")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @Operation(summary = "预览文件",
               description = "该接口用于在线预览存储在服务器上的文件。用户通过URL路径参数指定待预览文件的存储位置，包括年（yyyy）、月（MM）、日（dd）及UUID格式的文件名。接口从指定路径读取文件内容并将其转换为适合预览的格式（如图片或文档预览快照）。接口返回一个HTTP响应实体，其主体包含了预览文件的二进制数据，以便于前端展示预览效果。")
    @GetMapping("/preview/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public ResponseEntity<byte[]> preview(@PathVariable("yyyy") String yyyy,
                                          @PathVariable("MM") String MM,
                                          @PathVariable("dd") String dd,
                                          @PathVariable("uuidFileName") String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.preview(fileUrl);
    }

}
