package com.xftxyz.doctorarrival.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 病人信息表
 *
 * @TableName patient
 */
@TableName(value = "patient")
@Data
public class Patient implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

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
     * 性别（0：女，1：男）
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 是否结婚（0：否，1：是）
     */
    @TableField(value = "is_marry")
    private Integer isMarry;

    /**
     * 出生日期
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 是否有医保（0：否，1：是）
     */
    @TableField(value = "is_insured")
    private Integer isInsured;

    /**
     * 就诊卡号
     */
    @TableField(value = "card_no")
    private String cardNo;

    /**
     * 联系人姓名
     */
    @TableField(value = "contacts_name")
    private String contactsName;

    /**
     * 联系人手机
     */
    @TableField(value = "contacts_phone")
    private String contactsPhone;

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
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}