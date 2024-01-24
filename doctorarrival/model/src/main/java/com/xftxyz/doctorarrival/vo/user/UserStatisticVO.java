package com.xftxyz.doctorarrival.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserStatisticVO {

    private Long phone;
    private Long wx;

    private Long total;

    private Date from;
    private Date to;
}
