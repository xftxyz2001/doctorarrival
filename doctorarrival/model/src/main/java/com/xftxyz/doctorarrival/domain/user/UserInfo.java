package com.xftxyz.doctorarrival.domain.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 *
 * @TableName user_info
 */
@TableName(value = "user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 微信openid
     */
    @TableField(value = "openid")
    private String openid;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 证件类型（1：身份证，2：军官证，3：护照）
     */
    @TableField(value = "certificates_type")
    private Integer certificatesType;

    /**
     * 证件编号
     */
    @TableField(value = "certificates_no")
    private String certificatesNo;

    /**
     * 证件路径
     */
    @TableField(value = "certificates_url")
    private String certificatesUrl;

    /**
     * 状态（0：未认证，1：认证中，2：认证成功，-1：认证失败）
     */
    @TableField(value = "auth_status")
    private Integer authStatus;

    /**
     * 认证有效期（结束时间）
     */
    @TableField(value = "auth_time")
    private Date authTime;

    /**
     * 状态（0：不可用，1：可用）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 逻辑删除（0:未删除，1:已删除）
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}