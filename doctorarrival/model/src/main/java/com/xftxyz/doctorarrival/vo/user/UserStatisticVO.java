package com.xftxyz.doctorarrival.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserStatisticVO {

    private Long phone;
    private Long wx;

    private Long total;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date from;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date to;
}
