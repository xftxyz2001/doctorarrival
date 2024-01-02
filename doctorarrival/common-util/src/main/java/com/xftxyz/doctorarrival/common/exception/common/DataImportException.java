package com.xftxyz.doctorarrival.common.exception.common;

import com.xftxyz.doctorarrival.common.exception.BusinessException;
import com.xftxyz.doctorarrival.common.result.Result;
import com.xftxyz.doctorarrival.common.result.ResultEnum;

public class DataImportException extends BusinessException {

    @Override
    public Result<?> handleBusinessException() {
        return Result.failed(ResultEnum.DATA_IMPORT_FAILED.getCode(), ResultEnum.DATA_IMPORT_FAILED.getMessage());
    }
}
