package com.xftxyz.doctorarrival.sms.controller;

import com.xftxyz.doctorarrival.sms.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "短信相关")
@RequestMapping("/api/sms/send")
public class SmsController {

    private final SmsService smsService;


    @Operation(summary = "发送验证码",
            description = "此接口用于向指定手机号发送验证码。通过HTTP POST请求，客户端需在路径参数中提供有效的手机号码，手机号格式必须符合中国大陆手机号码的正则表达式要求，即以1开头，第二位为3-9之间的数字，随后跟随9位数字。若手机号格式不正确，服务端将返回错误提示。调用该接口后，短信服务将会发送一条验证码短信至指定手机，接口返回一个布尔值，表示验证码发送操作是否成功。")
    @PostMapping("/code/{phoneNumber}")
    public Boolean sendVerificationCode(@PathVariable("phoneNumber")
                                        @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
                                        String phoneNumber) {
        return smsService.sendVerificationCode(phoneNumber);
    }

}
