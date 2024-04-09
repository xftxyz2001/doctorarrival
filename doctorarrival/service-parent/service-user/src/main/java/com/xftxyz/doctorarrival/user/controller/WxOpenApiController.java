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

    @Operation(summary = "验证消息签名",
               description = "此接口用于验证微信服务器推送的消息签名是否合法。微信服务器在推送消息时会携带signature、timestamp、nonce和echostr四个参数，开发者需要验证这些参数是否合法。")
    @GetMapping("/verify")
    @ResponseBody
    public String verifySignature(@RequestParam("signature") String signature,
                                  @RequestParam("timestamp") String timestamp,
                                  @RequestParam("nonce") String nonce,
                                  @RequestParam("echostr") String echostr) {
        return wxOpenService.verifySignature(signature, timestamp, nonce, echostr);
    }

    @Operation(summary = "获取登陆二维码相关参数",
               description = "此接口用于获取微信开放平台登录二维码的相关参数，这些参数可用于生成微信扫码登录二维码。接口返回一个WxLoginQrCodeParam对象，其中包含了生成二维码所需的必要参数，如临时授权码、随机字符串和登录状态标识等。客户端利用返回的参数生成二维码，用户扫描后进入下一步登录流程。")
    @GetMapping("/qrcode")
    @ResponseBody
    public WxLoginQrCodeParam getWxLoginQrCodeParam() {
        return wxOpenService.getWxLoginQrCodeParam();
    }

    @Operation(summary = "登录回调",
               description = "当用户在微信内扫描二维码并完成授权后，微信服务器将跳转至本接口，并携带code和state两个参数。接口接收到这两个参数后，通过wxOpenService处理登录回调逻辑，验证授权信息并完成用户登录流程。最终，该接口通常会返回一个重定向地址或者处理完登录后的相应信息。")
    @GetMapping("/callback")
    public String loginCallback(@RequestParam("code") String code,
                                @RequestParam("state") String state) {
        return wxOpenService.loginCallback(code, state);
    }
}
