package com.xftxyz.doctorarrival.vo.order;

import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoQueryVO {
    /**
     * 医院编号或名称
     */
    private String hospitalCodeOrName;

    /**
     * 科室编号或名称
     */
    private String departmentCodeOrName;

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 就诊人id或名称
     */
    private String patientIdOrName;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 创建时间
     */
    private Date createTimeFrom;

    private Date createTimeTo;
}
