package com.xftxyz.doctorarrival.vo.hospital;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class HospitalStatisticVO {

    private Long count;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date from;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date to;
}
