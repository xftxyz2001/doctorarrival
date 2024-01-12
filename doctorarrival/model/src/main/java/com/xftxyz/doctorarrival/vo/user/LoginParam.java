package com.xftxyz.doctorarrival.vo.user;

import lombok.Data;

@Data
public class LoginParam {

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 验证码
     */
    private String verificationCode;
}
