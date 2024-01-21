package com.xftxyz.mock.mockhospital.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Schedule {
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
     * 排班编号
     */
    private String id;
}
