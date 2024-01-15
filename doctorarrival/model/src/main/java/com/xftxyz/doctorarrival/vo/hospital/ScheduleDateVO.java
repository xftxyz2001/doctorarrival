package com.xftxyz.doctorarrival.vo.hospital;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ScheduleDateVO {
    /**
     * 医院编号
     */
    private String hospitalCode;

    /**
     * 科室编号
     */
    private String departmentCode;

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
     * 可预约数
     */
    private Integer reservedNumber;

    /**
     * 剩余预约数
     */
    private Integer availableNumber;

    /**
     * 排班状态（-1：停诊 0：停约 1：可约）
     */
    private Integer status;

}
