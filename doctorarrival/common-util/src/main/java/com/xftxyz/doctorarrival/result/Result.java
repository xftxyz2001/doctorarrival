package com.xftxyz.doctorarrival.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局统一返回结果类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static Result<?> failed(String message) {
        return new Result<>(ResultEnum.FAILED.getCode(), message, null);
    }

    public static Result<?> failed(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}