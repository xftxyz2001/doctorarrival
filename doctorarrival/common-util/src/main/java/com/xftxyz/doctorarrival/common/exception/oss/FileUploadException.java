package com.xftxyz.doctorarrival.common.exception.oss;

import com.xftxyz.doctorarrival.common.exception.BusinessException;
import com.xftxyz.doctorarrival.common.result.Result;
import com.xftxyz.doctorarrival.common.result.ResultEnum;

public class FileUploadException extends BusinessException {

    @Override
    public Result<?> handleBusinessException() {
        return Result.failed(ResultEnum.FILE_UPLOAD_FAILED.getCode(), ResultEnum.FILE_UPLOAD_FAILED.getMessage());
    }
}
