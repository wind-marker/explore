# XXL-Job Demo 使用说明

这是一个演示如何使用 XXL-Job 的示例项目。

## 项目说明

本项目展示了如何使用 `xxl-job-starter` 快速集成 XXL-Job 分布式任务调度框架，并提供了多种实际业务场景的任务示例。

## 前置条件

在运行本 Demo 之前，需要先部署 XXL-Job 调度中心（xxl-job-admin）。

### 快速部署 XXL-Job Admin

#### 方式一：使用 Docker（推荐）

```bash
docker run -d \
  --name xxl-job-admin \
  -p 8080:8080 \
  -e PARAMS="--spring.datasource.url=jdbc:mysql://host.docker.internal:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai \
  --spring.datasource.username=root \
  --spring.datasource.password=root" \
  xuxueli/xxl-job-admin:2.3.1
```

如果不使用 MySQL，可以使用内存数据库（仅用于测试）：

```bash
docker run -d \
  --name xxl-job-admin \
  -p 8080:8080 \
  xuxueli/xxl-job-admin:2.3.1
```

#### 方式二：下载源码部署

1. 下载 XXL-Job 源码：https://github.com/xuxueli/xxl-job
2. 创建数据库 `xxl_job`，执行初始化脚本（doc/db/tables_xxl_job.sql）
3. 修改配置文件 `xxl-job-admin/src/main/resources/application.properties`
4. 启动 `xxl-job-admin` 项目

### 访问调度中心

- 访问地址：http://localhost:8080/xxl-job-admin
- 默认账号：admin
- 默认密码：123456

## 快速开始

### 1. 启动 Demo 应用

```bash
cd xxl-job-demo
mvn spring-boot:run
```

或者在 IDE 中直接运行 `XxlJobDemoApplication` 类。

### 2. 在调度中心注册执行器

1. 登录调度中心
2. 进入"执行器管理"页面
3. 点击"新增执行器"，配置如下：
   - AppName: `xxl-job-executor-demo`（与配置文件中的 `xxl.job.executor.appname` 一致）
   - 名称: XXL-Job Demo 执行器
   - 注册方式: 自动注册
   - 机器地址: 留空（执行器会自动注册）

### 3. 配置任务

进入"任务管理"页面，点击"新增任务"，配置示例任务。

#### 示例 1: 简单任务

- 执行器: 选择 `xxl-job-executor-demo`
- 任务描述: 简单任务示例
- 负责人: admin
- 调度类型: CRON
- Cron: `0 0/1 * * * ?` (每分钟执行一次)
- 运行模式: BEAN
- JobHandler: `simpleJobHandler`
- 阻塞处理策略: 单机串行
- 失败重试次数: 0

#### 示例 2: 带参数任务

- JobHandler: `paramJobHandler`
- 任务参数: `{"type":"test","count":100}`

#### 示例 3: 分片任务

- JobHandler: `shardingJobHandler`
- 路由策略: 分片广播

### 4. 启动任务

在任务列表中，点击"操作"列的"启动"按钮，启动任务。

### 5. 查看执行日志

在任务列表中，点击"操作"列的"日志"按钮，查看任务执行日志和结果。

## 示例任务说明

本 Demo 提供了两类任务处理器：

### SampleJobHandler - 基础示例

| JobHandler | 说明 | 演示特性 |
|-----------|------|---------|
| simpleJobHandler | 简单任务 | 最基础的任务执行 |
| paramJobHandler | 带参数任务 | 获取和使用任务参数 |
| shardingJobHandler | 分片任务 | 任务分片处理 |
| resultJobHandler | 返回值任务 | 设置任务执行结果 |
| dailyReportJobHandler | 定时报表任务 | 模拟生成报表 |
| dataCleanupJobHandler | 数据清理任务 | 模拟清理过期数据 |

### BusinessJobHandler - 业务场景

| JobHandler | 说明 | 业务场景 |
|-----------|------|---------|
| orderTimeoutCancelHandler | 订单超时取消 | 定时取消超时未支付订单 |
| userPointsExpiryHandler | 积分过期处理 | 处理即将过期和已过期积分 |
| systemStatisticsHandler | 系统数据统计 | 定时统计系统关键指标 |
| cacheWarmupHandler | 缓存预热 | 在低峰期预加载热点数据 |
| dataSyncHandler | 数据同步 | 定时同步数据到其他系统（支持分片） |

## 配置说明

### application.yml 配置项

```yaml
xxl:
  job:
    enabled: true                                      # 是否启用
    admin-addresses: http://localhost:8080/xxl-job-admin  # 调度中心地址
    executor:
      appname: xxl-job-executor-demo                   # 执行器名称
      port: 9999                                       # 执行器端口
      log-path: ./logs/xxl-job/jobhandler             # 日志路径
      log-retention-days: 30                           # 日志保留天数
      access-token:                                    # 访问令牌（可选）
```

### 常用 Cron 表达式

| 表达式 | 说明 |
|-------|------|
| `0 0/1 * * * ?` | 每分钟执行一次 |
| `0 0/5 * * * ?` | 每5分钟执行一次 |
| `0 0 * * * ?` | 每小时执行一次 |
| `0 0 0 * * ?` | 每天凌晨执行 |
| `0 0 2 * * ?` | 每天凌晨2点执行 |
| `0 0 0 * * 1` | 每周一凌晨执行 |
| `0 0 0 1 * ?` | 每月1号凌晨执行 |

## 路由策略说明

- **FIRST（第一个）**: 固定选择第一个机器
- **LAST（最后一个）**: 固定选择最后一个机器
- **ROUND（轮询）**: 依次选择在线的机器
- **RANDOM（随机）**: 随机选择在线的机器
- **CONSISTENT_HASH（一致性HASH）**: 每个任务按照Hash算法固定选择某一台机器
- **LEAST_FREQUENTLY_USED（最不经常使用）**: 使用频率最低的机器优先被选举
- **LEAST_RECENTLY_USED（最近最久未使用）**: 最久未使用的机器优先被选举
- **FAILOVER（故障转移）**: 按照顺序依次进行心跳检测，第一个心跳检测成功的机器选定为目标执行器
- **BUSYOVER（忙碌转移）**: 按照顺序依次进行空闲检测，第一个空闲的机器选定为目标执行器
- **SHARDING_BROADCAST（分片广播）**: 广播触发对应集群中所有机器执行一次任务，同时传递分片参数

## 注意事项

1. **调度中心地址**: 确保 `xxl.job.admin-addresses` 配置正确，且调度中心服务正常运行
2. **执行器 AppName**: 必须在调度中心注册对应的执行器，AppName 要保持一致
3. **端口占用**: 确保执行器端口（默认 9999）未被占用
4. **日志路径**: 确保日志路径有写入权限
5. **访问令牌**: 如果调度中心配置了 access token，执行器也需要配置相同的 token

## 故障排查

### 执行器未注册成功

1. 检查调度中心地址是否正确
2. 检查网络连接是否正常
3. 查看应用日志，确认执行器是否正常启动
4. 确认 AppName 是否在调度中心注册

### 任务不执行

1. 检查任务是否已启动
2. 检查 Cron 表达式是否正确
3. 检查 JobHandler 名称是否与代码中的 @XxlJob 注解值一致
4. 查看调度日志，确认是否有错误信息

### 查看详细日志

执行器日志位置：`./logs/xxl-job/jobhandler/`

## 扩展开发

### 添加新的任务处理器

1. 创建一个新的 `@Component` 类
2. 在方法上添加 `@XxlJob("jobHandlerName")` 注解
3. 在调度中心配置对应的任务

示例：

```java
@Component
public class CustomJobHandler {
    
    @XxlJob("customJobHandler")
    public void customJobHandler() {
        // 业务逻辑
    }
}
```

## 参考资源

- [XXL-Job 官方文档](https://www.xuxueli.com/xxl-job/)
- [XXL-Job GitHub](https://github.com/xuxueli/xxl-job)
- [Cron 表达式在线生成器](https://cron.qqe2.com/)
