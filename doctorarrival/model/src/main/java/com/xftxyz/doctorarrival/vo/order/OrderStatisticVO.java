package com.xftxyz.doctorarrival.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatisticVO {

    private Long closed;
    private BigDecimal closedAmount;

    private Long unpaid;
    private BigDecimal unpaidAmount;

    private Long paid;
    private BigDecimal paidAmount;

    private Long refunding;
    private BigDecimal refundingAmount;

    private Long refunded;
    private BigDecimal refundedAmount;

    private Long completed;
    private BigDecimal completedAmount;

    private Long total;
    private BigDecimal totalAmount;

    private Date from;
    private Date to;
}
