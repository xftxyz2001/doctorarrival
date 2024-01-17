package com.xftxyz.doctorarrival.exception;

import com.xftxyz.doctorarrival.result.Result;
import com.xftxyz.doctorarrival.result.ResultEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice(basePackages = "com.xftxyz.doctorarrival")
public class GlobalExceptionHandler {

    /**
     * 捕获 {@link BusinessException} 异常，交由
     * {@link BusinessException#handleBusinessException()} 处理
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        return e.handleBusinessException();
    }


    // 请求参数校验失败异常

    /**
     * {@link RequestBody} 参数校验不通过时抛出的异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<String> errorMessages = result.getFieldErrors().stream()
                .map(error -> String.format("%s : %s", error.getField(), error.getDefaultMessage()))
                .toList();
        return Result.failed(ResultEnum.PARAM_VALID_ERROR.getCode(), "请求参数校验失败" + errorMessages);
    }

    /**
     * {@link PathVariable} 和 {@link RequestParam} 参数校验不通过时抛出的异常处理
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> errorMessages = violations.stream()
                .map(violation -> String.format("%s : %s", violation.getPropertyPath(), violation.getMessage()))
                .toList();
        return Result.failed(ResultEnum.PARAM_VALID_ERROR.getCode(), "请求参数校验失败" + errorMessages);
    }

    // 请求参数缺失
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Result.failed(ResultEnum.PARAM_ERROR.getCode(),
                ResultEnum.PARAM_ERROR.getMessage() + ": " + e.getParameterName() + "不能为空");
    }

    // HTTP消息不可读异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Result.failed(ResultEnum.PARAM_ERROR.getCode(),
                ResultEnum.PARAM_ERROR.getMessage());
    }

    // 请求参数异常
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return Result.failed(ResultEnum.PARAM_ERROR.getCode(),
                ResultEnum.PARAM_ERROR.getMessage() + ": " + e.getName() + "应为" + e.getRequiredType());
    }

    // 顶级异常捕获并统一处理，当其他异常无法处理时候选择使用
    @ExceptionHandler(Exception.class)
    public Result<?> handle(Exception e) {
        log.error("出现未知异常：", e);
        return Result.failed(e.getMessage());
    }
}
