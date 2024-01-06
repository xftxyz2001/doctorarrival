package com.xftxyz.doctorarrival.sdk.api;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 更新排班请求
 */
@Data
public class UpdateScheduleRequest {
    // 停诊
    public static final Integer STATUS_STOP = -1;
    // 停约
    public static final Integer STATUS_DISABLE = 0;
    // 可约
    public static final Integer STATUS_ENABLE = 1;

    /**
     * 科室编号
     */
    private String departmentCode;

    /**
     * 职称
     */
    private String doctorTitle;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 擅长技能
     */
    private String skill;

    /**
     * 排班日期
     */
    private Date workDate;

    /**
     * 排班时间
     */
    private String workTime;

    /**
     * 可预约数
     */
    private Integer reservedNumber;

    /**
     * 剩余预约数
     */
    private Integer availableNumber;

    /**
     * 挂号费
     */
    private BigDecimal amount;

    /**
     * 排班状态（-1：停诊 0：停约 1：可约）
     */
    private Integer status;

    /**
     * 排班编号（医院自己的排班主键）
     */
    private String hospitalScheduleId;
}
