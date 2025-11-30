package com.clx.learn.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

/**
 * 日志配置属性类
 */
@ConfigurationProperties(prefix = "log.starter")
public class LogProperties {
    
    /**
     * 是否启用日志功能
     */
    private boolean enabled = true;
    
    /**
     * 默认日志级别
     */
    private LogLevel defaultLevel = LogLevel.INFO;
    
    // Getters and Setters
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public LogLevel getDefaultLevel() {
        return defaultLevel;
    }
    
    public void setDefaultLevel(LogLevel defaultLevel) {
        this.defaultLevel = defaultLevel;
    }
}