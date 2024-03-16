package com.xftxyz.doctorarrival.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "请求检查")
@RestController
public class CheckRequestController {

    @Operation(summary = "检查请求是否有权限")
    @PostMapping("/admin/common/check")
    public String checkAuthorization() {
        return "ok";
    }
}
