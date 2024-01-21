package com.xftxyz.doctorarrival.domain.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 *
 * @TableName order_info
 */
@TableName(value = "order_info")
@Data
public class OrderInfo implements Serializable {
    // （-1：已关闭，0：待支付，1：已支付，2：待退款，3：已退款，4：已完成）
    public static final Integer ORDER_STATUS_CLOSED = -1;
    public static final Integer ORDER_STATUS_UNPAID = 0;
    public static final Integer ORDER_STATUS_PAID = 1;
    public static final Integer ORDER_STATUS_REFUNDING = 2;
    public static final Integer ORDER_STATUS_REFUNDED = 3;
    public static final Integer ORDER_STATUS_COMPLETED = 4;

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
    private String scheduleId;

    /**
     * 预约时间
     */
    @TableField(value = "reserve_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * 订单金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 订单状态（-1：已关闭，0：待支付，1：已支付，2：待退款，3：已退款，4：已完成）
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

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