package com.xftxyz.doctorarrival.domain.order;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 *
 * @TableName order_info
 */
@TableName(value = "order_info")
@Data
public class OrderInfo implements Serializable {
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
     * 订单交易号
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;

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
     * 科室编号
     */
    @TableField(value = "department_code")
    private String departmentCode;

    /**
     * 科室名称
     */
    @TableField(value = "department_name")
    private String departmentName;

    /**
     * 医生名称
     */
    @TableField(value = "doctor_name")
    private String doctorName;

    /**
     * 医生职称
     */
    @TableField(value = "doctor_title")
    private String doctorTitle;

    /**
     * 排班id
     */
    @TableField(value = "schedule_id")
    private Long scheduleId;

    /**
     * 预约时间
     */
    @TableField(value = "reserve_date")
    private Date reserveDate;

    /**
     * 就诊人id
     */
    @TableField(value = "patient_id")
    private Long patientId;

    /**
     * 就诊人名称
     */
    @TableField(value = "patient_name")
    private String patientName;

    /**
     * 就诊人手机
     */
    @TableField(value = "patient_phone")
    private String patientPhone;

    /**
     * 订单状态（0：支付中，1：已支付，3：已完成
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

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