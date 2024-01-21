package com.xftxyz.doctorarrival.sdk.callback;

import lombok.Data;

import java.util.Date;

/**
 * 下单
 */
@Data
public class UpdateOrderRequest {
    public static final Integer ORDER_STATUS_CLOSED = -1;
    public static final Integer ORDER_STATUS_UNPAID = 0;
    public static final Integer ORDER_STATUS_PAID = 1;
    public static final Integer ORDER_STATUS_REFUNDING = 2;
    public static final Integer ORDER_STATUS_REFUNDED = 3;
    public static final Integer ORDER_STATUS_COMPLETED = 4;

    /**
     * id
     */
    private Long id;

    /**
     * 科室编号
     */
    private String departmentCode;

    /**
     * 科室名称
     */
    private String departmentName;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 排班id
     */
    private Long scheduleId;

    /**
     * 预约时间
     */
    private Date reserveDate;

    /**
     * 就诊人名称
     */
    private String patientName;

    /**
     * 就诊人手机
     */
    private String patientPhone;

    /**
     * 订单金额
     */
    private Integer amount;

    /**
     * 订单状态（-1：已关闭，0：待支付，1：已支付，2：待退款，3：已退款，4：已完成）
     */
    private Integer orderStatus;

    /**
     * 创建时间
     */
    private Date createTime;
}
