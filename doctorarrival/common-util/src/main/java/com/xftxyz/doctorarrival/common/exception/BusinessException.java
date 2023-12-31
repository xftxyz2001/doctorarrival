package com.xftxyz.doctorarrival.common.exception;

import com.xftxyz.doctorarrival.common.result.Result;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public Result<?> handleBusinessException() {
        return Result.failed(getMessage());
    }

}
