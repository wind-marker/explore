package com.clx.learn.log.aop;

import com.clx.learn.log.annotation.AutoLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志切面类
 * 处理@AutoLog注解，自动记录方法的入参和返回值
 */
@Aspect
@Component
public class LogAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    
    @Around("@annotation(autoLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, AutoLog autoLog) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        
        // 构建日志前缀
        String prefix = autoLog.prefix().isEmpty() ? 
            String.format("[%s.%s]", className, methodName) : 
            autoLog.prefix();
        
        // 记录入参
        if (autoLog.logArgs()) {
            Object[] args = joinPoint.getArgs();
            String argsString = Arrays.toString(args);
            logMessage(autoLog.level(), String.format("%s 参数: %s", prefix, argsString));
        }
        
        long startTime = System.currentTimeMillis();
        Object result = null;
        Exception exception = null;
        
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            
            // 记录返回值或异常
            if (exception != null) {
                logMessage(autoLog.level(), String.format("%s 异常: %s (%d ms)", 
                    prefix, exception.getClass().getSimpleName() + ": " + exception.getMessage(), 
                    endTime - startTime));
            } else if (autoLog.logResult()) {
                logMessage(autoLog.level(), String.format("%s 返回值: %s (%d ms)", 
                    prefix, result, endTime - startTime));
            } else {
                logMessage(autoLog.level(), String.format("%s 执行完成 (%d ms)", 
                    prefix, endTime - startTime));
            }
        }
    }
    
    /**
     * 根据日志级别记录日志
     */
    private void logMessage(LogLevel level, String message) {
        switch (level) {
            case DEBUG:
                logger.debug(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            case INFO:
            default:
                logger.info(message);
                break;
        }
    }
}