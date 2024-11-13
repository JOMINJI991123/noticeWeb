package com.example.noticeweb.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DebuggingAspect {
    @Pointcut("execution( * com.example.hongtack.api.*.*(..))")
    private void cut() {}

    @Before("cut()")
    public void loggingArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();

        String methodName = joinPoint.getSignature()
                .getName();

        for(Object obj : args) {
            log.info("{}#{}의 입력값 => {}",className,methodName,obj);
        }
    }

    @AfterReturning(value="cut()",returning = "returnObj")
    public void loggingReturnValue(JoinPoint joinPoint,
                                   Object returnObj) {

        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();

        String methodName = joinPoint.getSignature()
                .getName();

        log.info("{}#{}의 반환값 => {}",className,methodName,returnObj);
    }
}
