package com.clx.learn.xxljob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * XXL-Job 自动配置类
 * 当满足条件时自动配置 XXL-Job 执行器
 */
@Configuration
@ConditionalOnClass(XxlJobSpringExecutor.class)
@ConditionalOnProperty(prefix = "xxl.job", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(XxlJobAutoConfiguration.class);

    /**
     * 创建并配置 XXL-Job Spring 执行器
     *
     * @param properties XXL-Job 配置属性
     * @return XxlJobSpringExecutor 实例
     */
    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties properties) {
        logger.info(">>>>>>>>>>> XXL-Job 配置初始化.");

        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        
        // 配置调度中心地址
        xxlJobSpringExecutor.setAdminAddresses(properties.getAdminAddresses());
        
        // 配置执行器 AppName
        xxlJobSpringExecutor.setAppname(properties.getExecutor().getAppname());
        
        // 配置执行器注册地址（可选）
        if (properties.getExecutor().getAddress() != null && !properties.getExecutor().getAddress().isEmpty()) {
            xxlJobSpringExecutor.setAddress(properties.getExecutor().getAddress());
        }
        
        // 配置执行器 IP（可选）
        if (properties.getExecutor().getIp() != null && !properties.getExecutor().getIp().isEmpty()) {
            xxlJobSpringExecutor.setIp(properties.getExecutor().getIp());
        }
        
        // 配置执行器端口号
        xxlJobSpringExecutor.setPort(properties.getExecutor().getPort());
        
        // 配置访问令牌（可选）
        if (properties.getExecutor().getAccessToken() != null && !properties.getExecutor().getAccessToken().isEmpty()) {
            xxlJobSpringExecutor.setAccessToken(properties.getExecutor().getAccessToken());
        }
        
        // 配置日志存储路径
        xxlJobSpringExecutor.setLogPath(properties.getExecutor().getLogPath());
        
        // 配置日志保留天数
        xxlJobSpringExecutor.setLogRetentionDays(properties.getExecutor().getLogRetentionDays());

        logger.info(">>>>>>>>>>> XXL-Job 执行器配置完成:");
        logger.info("  - Admin Addresses: {}", properties.getAdminAddresses());
        logger.info("  - AppName: {}", properties.getExecutor().getAppname());
        logger.info("  - Port: {}", properties.getExecutor().getPort());
        logger.info("  - Log Path: {}", properties.getExecutor().getLogPath());

        return xxlJobSpringExecutor;
    }
}
