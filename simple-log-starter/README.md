# Auto Log Starter

一个基于注解的Spring Boot Starter，用于自动记录方法的入参和返回值。

## starter创建步骤
1.引入依赖
2.创建自动配置类
3.创建配置属性类
4.创建spring.factories，并指定自动配置类路径

## 功能特性

- 通过简单的注解 `@AutoLog` 自动记录方法调用的入参和返回值
- 支持自定义日志级别（DEBUG/INFO/WARN/ERROR）
- 可选择是否记录入参或返回值
- 自动计算方法执行时间
- 支持自定义日志前缀
- 异常情况自动记录异常信息

## 快速开始

### 1. 添加依赖

在你的 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>com.clx.learn</groupId>
    <artifactId>simple-log-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置属性（可选）

在 `application.yml` 或 `application.properties` 中配置日志属性：

```yaml
log:
  starter:
    enabled: true          # 是否启用日志功能，默认为true
```

### 3. 使用注解

在需要记录日志的方法上添加 `@AutoLog` 注解：

## 注解参数说明

| 参数 | 类型 | 默认值 | 描述 |
|------|------|--------|------|
| level | LogLevel | INFO | 日志级别 |
| logArgs | boolean | true | 是否记录入参 |
| logResult | boolean | true | 是否记录返回值 |
| prefix | String | "" | 自定义日志前缀 |

## 日志输出示例

```
2023-XX-XX XX:XX:XX.XXX INFO  [UserService.getUserById] 参数: [1]
2023-XX-XX XX:XX:XX.XXX INFO  [UserService.getUserById] 返回值: User(id=1, name="张三") (15 ms)
```

## 自动配置

该starter通过 `LogAutoConfiguration` 类自动配置，满足以下条件时会自动生效：
1. 类路径中存在 `LogAspect` 类
2. `log.starter.enabled` 属性为true（默认值）