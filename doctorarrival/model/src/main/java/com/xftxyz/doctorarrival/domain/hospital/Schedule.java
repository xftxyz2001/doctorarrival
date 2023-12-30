package com.xftxyz.doctorarrival.domain.hospital;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 排班
 */
@Data
@Document("Schedule")
public class Schedule {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除（0：未删除，1：已删除）
     */
    // private Integer isDeleted;

    /**
     * 医院编号
     */
    @Indexed // 普通索引
    private String hospitalCode;

    /**
     * 科室编号
     */
    @Indexed // 普通索引
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;

    /**
     * 排班时间（0：上午 1：下午）
     */
    private Integer workTime;

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
    @Indexed // 普通索引
    private String hospitalScheduleId;

}
