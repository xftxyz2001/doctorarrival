package com.xftxyz.mock.mockhospital.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderInfo {
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
     * 医生职称
     */
    private String doctorTitle;

    /**
     * 排班id
     */
    private String scheduleId;

    /**
     * 预约时间
     */
    private Date reserveDate;

    /**
     * 就诊人id
     */
    private Long patientId;

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
    private BigDecimal amount;

    /**
     * 订单状态（-1：已关闭，0：待支付，1：已支付，2：待退款，3：已退款，4：已完成）
     */
    private Integer orderStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
