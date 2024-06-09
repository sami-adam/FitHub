package com.fithub.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitoring {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMonitoring.class);
    @Value("${enable-performance-monitoring}")
    private boolean enablePerformanceMonitoring;

    @Around("execution(* com.fithub.service.*.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        if(!enablePerformanceMonitoring){
            return joinPoint.proceed();
        }
        long start = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        LOGGER.info("{} took {} ms", joinPoint.getSignature().getName(), (System.currentTimeMillis() - start));
        return object;
    }
}
