package com.xftxyz.doctorarrival.vo.hospital;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class HospitalJoinVO {

    /**
     * 医院编号
     */
    @NotBlank
    private String hospitalCode;

    /**
     * 医院名称
     */
    @NotBlank
    private String hospitalName;

    /**
     * api基础路径
     */
    @NotBlank
    private String apiUrl;

    /**
     * 联系人
     */
    private String contactsName;

    /**
     * 联系人手机
     */
    @Pattern(regexp = "1[3-9]\\d{9}", message = "手机号格式错误")
    private String contactsPhone;

    /**
     * 手机验证码
     */
    private String verificationCode;
}
