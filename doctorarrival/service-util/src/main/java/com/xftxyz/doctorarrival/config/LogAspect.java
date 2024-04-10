package com.xftxyz.doctorarrival.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogAspect {

    private final ObjectMapper objectMapper;

    // @Pointcut("execution(* com.xftxyz.doctorarrival.*.service.impl.*.*(..))")
    // public void serviceMethod() {
    // }

    @Pointcut("execution(* com.xftxyz.doctorarrival.*.controller.*.*(..))")
    public void controllerMethod() {
    }

    // 打印方法的出入参
    // @Around("serviceMethod() || controllerMethod()")
    @Around("controllerMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object result = joinPoint.proceed();
        log.info("{}.{}\n入参: {}\n出参: {}\n" +
                 "----------------------------------------------------------------------------------------------------",
                className, methodName, safeWriteValueAsString(args), safeWriteValueAsString(result));
        return result;
    }

    private String safeWriteValueAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return String.valueOf(obj);
        }
    }

}
