package com.clx.learn.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootApplication
public class TestLog {

    @Autowired
    private AutoLogDemo autoLogDemo;

    @Test
    public void testAutoLogFunction() {
        // 测试带参数的方法
        String result1 = autoLogDemo.processData("testData", 123);

        // 测试无参数的方法
        String result2 = autoLogDemo.getDefaultValue();

        // 测试void方法
        autoLogDemo.performAction();

        // 验证方法正常执行
        assert result1 != null;
        assert result2 != null;
    }

    @Test
    public void testExceptionLogging() {
        try {
            autoLogDemo.throwException();
        } catch (RuntimeException e) {
            // 验证异常被正确抛出和记录
            assert e.getMessage().contains("Test exception");
        }
    }
}

