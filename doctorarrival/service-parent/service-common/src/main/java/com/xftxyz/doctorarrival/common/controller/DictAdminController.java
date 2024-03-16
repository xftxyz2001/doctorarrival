package com.xftxyz.doctorarrival.common.controller;

import com.xftxyz.doctorarrival.common.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "数据字典管理")
@RequestMapping("/admin/common/dict")
public class DictAdminController {

    private final DictService dictService;

    @Operation(summary = "导入数据字典")
    @PostMapping("/import")
    public Boolean importDict(@RequestPart("file") @NotNull MultipartFile file) {
        return dictService.importDict(file);
    }

    @Operation(summary = "导出数据字典")
    @PostMapping("/export")
    public ResponseEntity<Resource> exportDict() {
        Resource resource = dictService.exportDict();
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"dict.xlsx\"")
                .body(resource);
    }
}
