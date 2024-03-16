package com.xftxyz.doctorarrival.user.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xftxyz.doctorarrival.user.service.WxOpenService;
import com.xftxyz.doctorarrival.vo.user.WxLoginQrCodeParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@JsonIgnoreProperties(ignoreUnknown = true)
@Controller
@RequiredArgsConstructor
@Tag(name = "微信开放平台")
@RequestMapping("/api/user/wx")
public class WxOpenApiController {

    private final WxOpenService wxOpenService;

    @Operation(summary = "获取登陆二维码相关参数")
    @GetMapping("/qrcode")
    @ResponseBody
    public WxLoginQrCodeParam getWxLoginQrCodeParam() {
        return wxOpenService.getWxLoginQrCodeParam();
    }

    @Operation(summary = "登录回调")
    @GetMapping("/callback")
    public String loginCallback(@RequestParam("code") String code,
                                @RequestParam("state") String state) {
        return wxOpenService.loginCallback(code, state);
    }
}
