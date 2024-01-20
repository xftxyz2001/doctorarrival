package com.xftxyz.doctorarrival.vo.hospital;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ScheduleVO {
    /**
     * 医院编号
     */
    private String hospitalCode;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 科室编号
     */
    private String departmentCode;

    /**
     * 科室名称
     */
    private String departmentName;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;

    /**
     * 日期对应的星期
     */
    private String dayOfWeek;

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
