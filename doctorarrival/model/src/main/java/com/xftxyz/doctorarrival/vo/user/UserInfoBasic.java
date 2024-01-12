package com.xftxyz.doctorarrival.vo.user;

import lombok.Data;

@Data
public class UserInfoBasic {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 昵称
     */
    private String nickName;
}
