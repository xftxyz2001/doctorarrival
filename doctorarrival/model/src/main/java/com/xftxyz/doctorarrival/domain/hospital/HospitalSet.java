package com.xftxyz.doctorarrival.domain.hospital;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 医院设置表
 *
 * @TableName hospital_set
 */
@TableName(value = "hospital_set")
@Data
public class HospitalSet implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 医院编号
     */
    @TableField(value = "hospital_code")
    private String hospitalCode;

    /**
     * 医院名称
     */
    @TableField(value = "hospital_name")
    private String hospitalName;

    /**
     * api基础路径
     */
    @TableField(value = "api_url")
    private String apiUrl;

    /**
     * 签名秘钥
     */
    @TableField(value = "sign_key")
    private String signKey;

    /**
     * 联系人
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