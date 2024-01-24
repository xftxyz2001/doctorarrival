package com.xftxyz.doctorarrival.vo.hospital;

import lombok.Data;

import java.util.Date;

@Data
public class HospitalStatisticVO {

    private Long count;

    private Date from;
    private Date to;
}
