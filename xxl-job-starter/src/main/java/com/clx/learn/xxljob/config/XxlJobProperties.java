package com.clx.learn.xxljob.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * XXL-Job 配置属性类
 * 用于读取和管理 XXL-Job 执行器的配置参数
 */
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    /**
     * 是否启用 XXL-Job，默认为 true
     */
    private boolean enabled = true;

    /**
     * XXL-Job 调度中心地址列表，多个地址用逗号分隔
     * 例如: http://localhost:8080/xxl-job-admin
     */
    private String adminAddresses;

    /**
     * 执行器配置
     */
    private Executor executor = new Executor();

    /**
     * 执行器配置类
     */
    public static class Executor {
        /**
         * 执行器 AppName，用于执行器注册和任务路由
         */
        private String appname = "xxl-job-executor";

        /**
         * 执行器注册地址
         * 为空则自动获取，多个地址用逗号分隔
         */
        private String address;

        /**
         * 执行器 IP
         * 为空则自动获取
         */
        private String ip;

        /**
         * 执行器端口号
         * 小于等于 0 则自动获取
         */
        private int port = 9999;

        /**
         * 执行器日志文件存储路径
         */
        private String logPath = "/data/applogs/xxl-job/jobhandler";

        /**
         * 执行器日志文件保留天数
         */
        private int logRetentionDays = 30;

        /**
         * 执行器访问令牌，用于安全校验
         */
        private String accessToken;

        // Getters and Setters
        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getLogPath() {
            return logPath;
        }

        public void setLogPath(String logPath) {
            this.logPath = logPath;
        }

        public int getLogRetentionDays() {
            return logRetentionDays;
        }

        public void setLogRetentionDays(int logRetentionDays) {
            this.logRetentionDays = logRetentionDays;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    // Getters and Setters
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAdminAddresses() {
        return adminAddresses;
    }

    public void setAdminAddresses(String adminAddresses) {
        this.adminAddresses = adminAddresses;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
}
