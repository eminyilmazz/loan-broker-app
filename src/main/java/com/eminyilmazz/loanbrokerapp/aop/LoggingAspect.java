package com.eminyilmazz.loanbrokerapp.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Around("execution(* com.eminyilmazz.loanbrokerapp.controller.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            try {
                logger.debug("{} method is called with arguments: {}", joinPoint.getSignature().getName(), objectMapper.writeValueAsString(arg));
            } catch (JsonProcessingException e) {
                logger.error("Error while serializing argument: {}", ExceptionUtils.getMessage(e));
            }
        }
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        logger.info("Method: {} completed in {}ms", joinPoint.getSignature().getName(), elapsedTime);
        return result;
    }
}
