package com.xftxyz.doctorarrival.oss.controller;

import com.xftxyz.doctorarrival.oss.service.FileService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    private final FileService fileService;

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

    @GetMapping("/preview/{yyyy}/{MM}/{dd}/{uuidFileName}")
    public ResponseEntity<byte[]> preview(@PathVariable("yyyy") String yyyy,
                                          @PathVariable("MM") String MM,
                                          @PathVariable("dd") String dd,
                                          @PathVariable("uuidFileName") String uuidFileName) {
        String fileUrl = yyyy + "/" + MM + "/" + dd + "/" + uuidFileName;
        return fileService.preview(fileUrl);
    }


}
