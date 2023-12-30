package com.xftxyz.doctorarrival.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.tools.Diagnostic;

@AllArgsConstructor
@Getter
public enum ResultEnum {

    // 失败
    FAILED(-1, "失败"), // 成功
    SUCCESS(0, "成功"),

    // 参数错误
    PARAM_ERROR(4000, "非法参数"),
    PARAM_VALID_ERROR(4001, "参数校验失败"),
    ;

    private final Integer code;
    private final String message;

}
