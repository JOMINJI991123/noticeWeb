package com.example.noticeweb.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Pointcut("@annotation(com.example.hongtack.annotation.RunningTime)")
    private void enableRunningTime(){}

    @Pointcut("execution(* com.example.hongtack..*.*(..))")
    private void cut() {}


    @Around("cut() && enableRunningTime()")
    public Object loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object returningObj = joinPoint.proceed();

        stopWatch.stop();

        String methodName = joinPoint.getSignature()
                .getName();

        log.info("around return 값 ={}",returningObj);
        log.info("{}의 총 수행 시간 ==> {}",methodName,stopWatch.getTotalTimeSeconds());

        return returningObj;
    }
}
