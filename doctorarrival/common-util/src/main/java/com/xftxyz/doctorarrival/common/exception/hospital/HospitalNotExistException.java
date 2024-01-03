package com.xftxyz.doctorarrival.common.exception.hospital;

import com.xftxyz.doctorarrival.common.exception.BusinessException;
import com.xftxyz.doctorarrival.common.result.Result;
import com.xftxyz.doctorarrival.common.result.ResultEnum;

public class HospitalNotExistException extends BusinessException {

    @Override
    public Result<?> handleBusinessException() {
        return Result.failed(ResultEnum.HOSPITAL_NOT_EXIST.getCode(), ResultEnum.HOSPITAL_NOT_EXIST.getMessage());
    }
}