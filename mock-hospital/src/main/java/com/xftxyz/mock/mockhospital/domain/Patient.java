package com.xftxyz.mock.mockhospital.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Patient {
    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 证件类型
     */
    private Integer certificatesType;

    /**
     * 证件编号
     */
    private String certificatesNo;

    /**
     * 性别（0：女，1：男）
     */
    private Integer gender;

    /**
     * 是否结婚（0：否，1：是）
     */
    private Integer marry;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 是否有医保（0：否，1：是）
     */
    private Integer insured;

    /**
     * 就诊卡号
     */
    private String cardNo;
}
