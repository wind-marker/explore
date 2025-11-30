package com.clx.learn.log.annotation;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动日志记录注解
 * 用于方法上，自动记录方法的入参和返回值
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoLog {
    
    /**
     * 日志级别
     */
    LogLevel level() default LogLevel.INFO;
    
    /**
     * 是否记录入参
     */
    boolean logArgs() default true;
    
    /**
     * 是否记录返回值
     */
    boolean logResult() default true;
    
    /**
     * 自定义日志前缀
     */
    String prefix() default "";
}