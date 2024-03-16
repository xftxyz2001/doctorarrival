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


    @Operation(summary = "发送验证码")
    @PostMapping("/code/{phoneNumber}")
    public Boolean sendVerificationCode(@PathVariable("phoneNumber")
                                        @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
                                        String phoneNumber) {
        return smsService.sendVerificationCode(phoneNumber);
    }

}
