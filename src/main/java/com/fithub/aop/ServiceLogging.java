package com.fithub.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;


@Aspect
@Component
public class ServiceLogging {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogging.class);
    @Before("execution(* com.fithub.service.*.*(..))")
    public void logServices(JoinPoint joinPoint){
        LOGGER.info("{} Called", joinPoint.getSignature().getName());
    }
}
