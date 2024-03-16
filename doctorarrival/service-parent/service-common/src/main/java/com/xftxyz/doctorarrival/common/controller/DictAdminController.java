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

    @Operation(summary = "导入数据字典",
               description = "接收并解析上传的文件（通常是Excel格式），用于导入系统中的数据字典项。要求上传的文件不能为空，成功导入则返回true，否则可能抛出异常或返回false。")
    @PostMapping("/import")
    public Boolean importDict(@RequestPart("file") @NotNull MultipartFile file) {
        return dictService.importDict(file);
    }

    @Operation(summary = "导出数据字典",
               description = "导出系统中现有的全部数据字典项至Excel文件。响应体是一个资源实体(Resource)，其内容类型为二进制流(application/octet-stream)，并自动设置了HTTP头以便浏览器识别并下载文件，文件名为'dict.xlsx'。")
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
