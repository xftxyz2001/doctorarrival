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

    @Operation(summary = "上传文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") @NotNull MultipartFile file) {
        return fileService.upload(file);
    }

    @Operation(summary = "预览文件")
    @GetMapping("/preview/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public ResponseEntity<byte[]> preview(@PathVariable("yyyy") String yyyy,
                                          @PathVariable("MM") String MM,
                                          @PathVariable("dd") String dd,
                                          @PathVariable("uuidFileName") String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.preview(fileUrl);
    }


}
