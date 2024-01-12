package com.xftxyz.doctorarrival.user.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xftxyz.doctorarrival.user.service.WxOpenService;
import com.xftxyz.doctorarrival.vo.user.WxLoginQrCodeParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/user/wx")
public class WxOpenApiController {

    private final WxOpenService wxOpenService;

    // 获取登陆二维码相关参数
    @GetMapping("/qrcode")
    @ResponseBody
    public WxLoginQrCodeParam getWxLoginQrCodeParam() {
        return wxOpenService.getWxLoginQrCodeParam();
    }

    // 登录回调
    @GetMapping("/callback")
    public String loginCallback(@RequestParam("code") String code,
                                @RequestParam("state") String state) {
        return wxOpenService.loginCallback(code, state);
    }
}
