package com.xftxyz.doctorarrival.exception;

import com.xftxyz.doctorarrival.result.Result;
import com.xftxyz.doctorarrival.result.ResultEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Result<?> handleBusinessException() {
        return Result.failed(this.code, this.getMessage());
    }

}
