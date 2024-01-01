package com.xftxyz.doctorarrival.common.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckRequestController {

    @PostMapping("/admin/common/check")
    public String checkAuthorization() {
        return "ok";
    }
}
