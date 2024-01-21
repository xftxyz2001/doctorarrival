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
     * 医院类型
     */
    private String hospitalType;

    /**
     * 省code
     */
    private String provinceCode;

    /**
     * 市code
     */
    private String cityCode;

    /**
     * 区code
     */
    private String districtCode;

    /**
     * 详情地址
     */
    private String address;

    /**
     * 医院logo
     */
    private String logoData;

    /**
     * 医院简介
     */
    private String intro;

    /**
     * 坐车路线
     */
    private String route;
}
