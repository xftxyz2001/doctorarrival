package com.xftxyz.doctorarrival.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Slf4j
@Aspect
public class LogAspect {

    @Pointcut("execution(* com.xftxyz.doctorarrival.*.service.impl.*.*(..))")
    public void serviceMethod() {
    }

    @Pointcut("execution(* com.xftxyz.doctorarrival.*.controller.*.*(..))")
    public void controllerMethod() {
    }

    // 打印方法的出入参
    @Around("serviceMethod() || controllerMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        log.info("{}.{} 入参: {}", className, methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();
        log.info("{}.{} 出参: {}", className, methodName, result);
        return result;
    }

}
