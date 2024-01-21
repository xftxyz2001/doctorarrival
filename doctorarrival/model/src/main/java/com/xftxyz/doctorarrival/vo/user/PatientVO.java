package com.xftxyz.doctorarrival.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PatientVO {
    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

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
     * 证件类型（name）
     */
    private String certificatesTypeName;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 是否有医保（0：否，1：是）
     */
    private Integer insured;

    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 联系人姓名
     */
    private String contactsName;

    /**
     * 联系人手机
     */
    private String contactsPhone;

    /**
     * 状态（0：不可用，1：可用）
     */
    private Integer status;
}
