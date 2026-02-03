# Explore - Spring Boot Starters 集合

这是一个包含多个 Spring Boot Starter 的项目集合，旨在快速集成常用功能和框架。

## 项目结构

```
explore/
├── simple-log-starter/          # 自动日志记录 Starter
├── xxl-job-starter/             # XXL-Job 集成 Starter
└── xxl-job-demo/                # XXL-Job 使用示例项目
```

## 模块说明

### 1. Simple Log Starter

一个基于注解的自动日志记录 Starter，可以自动记录方法的入参、返回值和执行时间。

**主要特性：**
- 通过 `@AutoLog` 注解自动记录方法调用信息
- 支持自定义日志级别（DEBUG/INFO/WARN/ERROR）
- 可选择是否记录入参或返回值
- 自动计算方法执行时间
- 支持自定义日志前缀

📖 [详细文档](./simple-log-starter/README.md)

**快速使用：**
```java
@Service
public class UserService {
    
    @AutoLog
    public User getUserById(Long id) {
        // 业务逻辑
        return user;
    }
}
```

### 2. XXL-Job Starter

XXL-Job 分布式任务调度框架的自动配置 Starter，提供开箱即用的任务调度能力。

**主要特性：**
- 自动配置 XXL-Job 执行器
- 支持通过配置文件灵活配置执行器参数
- 与 Spring Boot 完美集成
- 支持 BEAN 模式任务处理

📖 [详细文档](./xxl-job-starter/README.md)

**快速使用：**
```java
@Component
public class JobHandler {
    
    @XxlJob("demoJobHandler")
    public void demoJobHandler() {
        // 任务逻辑
    }
}
```

### 3. XXL-Job Demo

一个完整的 XXL-Job 使用示例项目，展示了如何使用 `xxl-job-starter` 集成任务调度功能。

**包含示例：**
- 简单任务示例
- 带参数的任务
- 分片任务处理
- 返回值任务
- 订单超时取消任务
- 用户积分过期处理
- 系统数据统计
- 缓存预热
- 数据同步任务

📖 [详细文档](./xxl-job-demo/README.md)

## 快速开始

### 环境要求

- JDK 8+
- Maven 3.5+
- Spring Boot 2.3.7.RELEASE

### 构建项目

```bash
# 克隆项目
git clone https://github.com/wind-marker/explore.git
cd explore

# 编译所有模块
mvn clean install

# 或者编译指定模块
cd xxl-job-starter
mvn clean install
```

### 使用 Starter

在你的项目 `pom.xml` 中添加依赖：

#### 使用 Simple Log Starter

```xml
<dependency>
    <groupId>com.clx.learn</groupId>
    <artifactId>simple-log-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### 使用 XXL-Job Starter

```xml
<dependency>
    <groupId>com.clx.learn</groupId>
    <artifactId>xxl-job-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 运行 Demo

```bash
# 运行 XXL-Job Demo
cd xxl-job-demo
mvn spring-boot:run
```

## 开发指南

### 如何创建一个 Starter

参考现有的 Starter 模块，一般需要以下步骤：

1. **创建 Maven 模块**
   - 命名规范：`xxx-starter`
   - 继承父 POM

2. **添加必要依赖**
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-autoconfigure</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-configuration-processor</artifactId>
       <optional>true</optional>
   </dependency>
   ```

3. **创建配置属性类**
   - 使用 `@ConfigurationProperties` 注解
   - 定义配置前缀和属性

4. **创建自动配置类**
   - 使用 `@Configuration` 注解
   - 使用 `@ConditionalOnClass` 等条件注解
   - 使用 `@EnableConfigurationProperties` 启用配置属性

5. **创建 spring.factories**
   - 在 `META-INF/spring.factories` 中注册自动配置类

6. **编写 README 文档**
   - 说明 Starter 的功能和用法
   - 提供配置示例和使用示例

## 技术栈

- Spring Boot 2.3.7.RELEASE
- Spring AOP（用于日志切面）
- XXL-Job 2.4.2（分布式任务调度）
- SLF4J（日志接口）
- Maven（构建工具）

## 贡献指南

欢迎贡献新的 Starter 或改进现有代码！

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

本项目采用 MIT 许可证。

## 联系方式

如有问题或建议，欢迎提交 Issue。

## 相关资源

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [XXL-Job 官方文档](https://www.xuxueli.com/xxl-job/)
- [Spring Boot Starter 开发指南](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-custom-starter)
