package com.xftxyz.doctorarrival.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoQueryVO {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 创建时间（开始）
     */
    private Date createTimeFrom;

    /**
     * 创建时间（结束）
     */
    private Date createTimeTo;
}
