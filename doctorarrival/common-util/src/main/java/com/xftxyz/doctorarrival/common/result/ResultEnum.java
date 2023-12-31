package com.xftxyz.doctorarrival.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultEnum {


    FAILED(-1, "失败"), // 失败
    SUCCESS(0, "成功"), // 成功

    // 1xxx 用户相关
    USER_NOT_LOGIN(1000, "用户未登录"),
    TOKEN_EXPIRED(1001, "token已过期"),
    PERMISSION_DENIED(1100, "权限不足"),

    // 参数错误,
    PARAM_ERROR(4000, "非法参数"),
    PARAM_VALID_ERROR(4001, "参数校验失败"),
    ;

    private final Integer code;
    private final String message;

}
