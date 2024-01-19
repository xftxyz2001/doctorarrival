package com.xftxyz.doctorarrival.vo.user;

import lombok.Data;

@Data
public class LoginResponse {

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * jwt token
     */
    private String token;
}
