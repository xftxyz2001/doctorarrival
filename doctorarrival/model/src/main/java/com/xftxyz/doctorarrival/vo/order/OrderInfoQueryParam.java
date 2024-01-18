package com.xftxyz.doctorarrival.vo.order;

import lombok.Data;

import java.util.Date;

@Data
public class OrderInfoQueryParam {

    /**
     * 就诊人id
     */
    private String patientId;

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
