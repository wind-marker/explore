package com.clx.learn.log.config;

import com.clx.learn.log.aop.LogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 日志自动配置类
 * 启用自动日志功能
 */
@Configuration
@EnableAspectJAutoProxy
@ConditionalOnClass(LogAspect.class)
@ConditionalOnProperty(prefix = "log.starter", value = "enabled", havingValue = "true", matchIfMissing = true)
public class LogAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}