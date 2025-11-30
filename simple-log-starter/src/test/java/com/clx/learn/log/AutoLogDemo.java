package com.clx.learn.log;

import com.clx.learn.log.annotation.AutoLog;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class AutoLogDemo {
    
    @AutoLog(level = LogLevel.INFO)
    public String processData(String data, Integer number) {
        return "Processed: " + data + ", " + number;
    }
    
    @AutoLog(level = LogLevel.DEBUG)
    public String getDefaultValue() {
        return "default";
    }
    
    @AutoLog(level = LogLevel.WARN)
    public void performAction() {
        System.out.println("Action performed");
    }
    
    @AutoLog(level = LogLevel.ERROR)
    public void throwException() {
        throw new RuntimeException("Test exception for logging");
    }
}
