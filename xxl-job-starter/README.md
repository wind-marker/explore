# XXL-Job Starter

一个基于 Spring Boot 的 XXL-Job 自动配置 Starter，用于快速集成分布式任务调度框架 XXL-Job。

## 功能特性

- 自动配置 XXL-Job 执行器
- 支持通过配置文件灵活配置执行器参数
- 开箱即用，无需手动配置 Bean
- 与 Spring Boot 完美集成

## 快速开始

### 1. 添加依赖

在你的 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>com.clx.learn</groupId>
    <artifactId>xxl-job-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置属性

在 `application.yml` 或 `application.properties` 中配置 XXL-Job 属性：

```yaml
xxl:
  job:
    enabled: true                                    # 是否启用 XXL-Job，默认为 true
    admin-addresses: http://localhost:8080/xxl-job-admin  # 调度中心地址
    executor:
      appname: xxl-job-executor                      # 执行器 AppName
      port: 9999                                     # 执行器端口号
      log-path: /data/applogs/xxl-job/jobhandler   # 日志文件存储路径
      log-retention-days: 30                         # 日志保留天数
      access-token:                                  # 访问令牌（可选）
```

或使用 `application.properties`：

```properties
xxl.job.enabled=true
xxl.job.admin-addresses=http://localhost:8080/xxl-job-admin
xxl.job.executor.appname=xxl-job-executor
xxl.job.executor.port=9999
xxl.job.executor.log-path=/data/applogs/xxl-job/jobhandler
xxl.job.executor.log-retention-days=30
```

### 3. 创建任务处理器

使用 `@XxlJob` 注解标记任务处理方法：

```java
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SampleJobHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(SampleJobHandler.class);
    
    /**
     * 简单任务示例
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() {
        logger.info("XXL-Job 简单任务执行开始");
        // 业务逻辑
        logger.info("XXL-Job 简单任务执行完成");
    }
}
```

### 4. 在调度中心配置任务

1. 访问 XXL-Job 调度中心（默认地址：http://localhost:8080/xxl-job-admin）
2. 登录系统（默认账号：admin，密码：123456）
3. 在"执行器管理"中添加执行器（AppName 需与配置文件中一致）
4. 在"任务管理"中添加任务，JobHandler 填写 `@XxlJob` 注解中的值（如：`demoJobHandler`）
5. 启动任务即可

## 配置参数说明

| 参数 | 类型 | 默认值 | 描述 |
|------|------|--------|------|
| xxl.job.enabled | boolean | true | 是否启用 XXL-Job |
| xxl.job.admin-addresses | String | - | 调度中心地址，多个用逗号分隔 |
| xxl.job.executor.appname | String | xxl-job-executor | 执行器 AppName |
| xxl.job.executor.address | String | - | 执行器注册地址（可选） |
| xxl.job.executor.ip | String | - | 执行器 IP（可选，为空则自动获取） |
| xxl.job.executor.port | int | 9999 | 执行器端口号 |
| xxl.job.executor.log-path | String | /data/applogs/xxl-job/jobhandler | 日志文件存储路径 |
| xxl.job.executor.log-retention-days | int | 30 | 日志保留天数 |
| xxl.job.executor.access-token | String | - | 访问令牌（可选） |

## 任务类型示例

### 1. BEAN 模式（方法任务）

```java
@XxlJob("simpleJobHandler")
public void simpleJobHandler() {
    // 业务逻辑
}
```

### 2. 带参数的任务

```java
@XxlJob("paramJobHandler")
public void paramJobHandler() {
    String param = XxlJobHelper.getJobParam();
    logger.info("任务参数: {}", param);
    // 使用参数执行业务逻辑
}
```

### 3. 分片任务

```java
@XxlJob("shardingJobHandler")
public void shardingJobHandler() {
    int shardIndex = XxlJobHelper.getShardIndex();
    int shardTotal = XxlJobHelper.getShardTotal();
    logger.info("分片参数: 当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);
    // 根据分片参数执行业务逻辑
}
```

## 自动配置

该 starter 通过 `XxlJobAutoConfiguration` 类自动配置，满足以下条件时会自动生效：

1. 类路径中存在 `XxlJobSpringExecutor` 类
2. `xxl.job.enabled` 属性为 true（默认值）

## 依赖版本

- Spring Boot: 2.3.7.RELEASE
- XXL-Job: 2.4.2
- Java: 8+

## 参考资源

- [XXL-Job 官方文档](https://www.xuxueli.com/xxl-job/)
- [XXL-Job GitHub](https://github.com/xuxueli/xxl-job)
