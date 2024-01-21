package com.xftxyz.doctorarrival.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoVO {
    /**
     * id
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    private String name;

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
     * 证件路径
     */
    private String certificatesUrl;

    /**
     * 状态（0：未认证，1：认证中，2：认证成功，-1：认证失败）
     */
    private Integer authStatus;

    /**
     * 认证有效期（结束时间）
     */
    private Date authTime;

    /**
     * 状态（0：不可用，1：可用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;
}
