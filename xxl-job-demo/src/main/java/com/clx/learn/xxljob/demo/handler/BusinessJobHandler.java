package com.clx.learn.xxljob.demo.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 业务场景任务处理器
 * 演示实际业务场景中的任务
 */
@Component
public class BusinessJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(BusinessJobHandler.class);
    private static final Random random = new Random();

    /**
     * 订单超时取消任务
     * 定时扫描超时未支付的订单并自动取消
     */
    @XxlJob("orderTimeoutCancelHandler")
    public void orderTimeoutCancelHandler() {
        logger.info("========== 订单超时取消任务开始 ==========");
        
        try {
            // 1. 查询超时订单
            logger.info("正在查询超时未支付订单...");
            TimeUnit.SECONDS.sleep(1);
            int timeoutOrderCount = random.nextInt(10) + 1;
            logger.info("查询到 {} 个超时订单", timeoutOrderCount);
            
            // 2. 处理超时订单
            int canceledCount = 0;
            for (int i = 1; i <= timeoutOrderCount; i++) {
                String orderId = "ORDER_" + System.currentTimeMillis() + "_" + i;
                logger.info("正在取消订单: {}", orderId);
                TimeUnit.MILLISECONDS.sleep(300);
                canceledCount++;
            }
            
            logger.info("成功取消 {} 个超时订单", canceledCount);
            XxlJobHelper.handleSuccess("成功取消 " + canceledCount + " 个超时订单");
            
        } catch (Exception e) {
            logger.error("订单超时取消任务执行失败", e);
            XxlJobHelper.handleFail("任务失败: " + e.getMessage());
        }
        
        logger.info("========== 订单超时取消任务完成 ==========");
    }

    /**
     * 用户积分过期任务
     * 定时检查并处理即将过期的用户积分
     */
    @XxlJob("userPointsExpiryHandler")
    public void userPointsExpiryHandler() {
        logger.info("========== 用户积分过期任务开始 ==========");
        
        try {
            // 1. 查询即将过期的积分
            logger.info("正在查询即将过期的积分记录...");
            TimeUnit.SECONDS.sleep(1);
            int expiringCount = random.nextInt(50) + 10;
            logger.info("查询到 {} 条即将过期的积分记录", expiringCount);
            
            // 2. 发送提醒通知
            logger.info("正在发送过期提醒通知...");
            TimeUnit.SECONDS.sleep(1);
            
            // 3. 处理已过期积分
            int expiredCount = random.nextInt(20) + 5;
            logger.info("正在处理 {} 条已过期积分...", expiredCount);
            TimeUnit.SECONDS.sleep(1);
            
            logger.info("积分过期处理完成: 提醒 {} 个用户，清理 {} 条过期记录", expiringCount, expiredCount);
            XxlJobHelper.handleSuccess(String.format("提醒 %d 个用户，清理 %d 条过期记录", expiringCount, expiredCount));
            
        } catch (Exception e) {
            logger.error("用户积分过期任务执行失败", e);
            XxlJobHelper.handleFail("任务失败: " + e.getMessage());
        }
        
        logger.info("========== 用户积分过期任务完成 ==========");
    }

    /**
     * 系统数据统计任务
     * 定时统计系统关键指标数据
     */
    @XxlJob("systemStatisticsHandler")
    public void systemStatisticsHandler() {
        logger.info("========== 系统数据统计任务开始 ==========");
        
        String statisticsDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("统计时间: {}", statisticsDate);
        
        try {
            // 1. 统计用户数据
            logger.info("正在统计用户数据...");
            TimeUnit.MILLISECONDS.sleep(500);
            int totalUsers = random.nextInt(10000) + 5000;
            int activeUsers = random.nextInt(1000) + 500;
            logger.info("总用户数: {}, 活跃用户数: {}", totalUsers, activeUsers);
            
            // 2. 统计订单数据
            logger.info("正在统计订单数据...");
            TimeUnit.MILLISECONDS.sleep(500);
            int totalOrders = random.nextInt(5000) + 1000;
            double totalAmount = random.nextDouble() * 100000 + 50000;
            logger.info("总订单数: {}, 总金额: {}", totalOrders, String.format("%.2f", totalAmount));
            
            // 3. 统计系统性能
            logger.info("正在统计系统性能...");
            TimeUnit.MILLISECONDS.sleep(500);
            double avgResponseTime = random.nextDouble() * 100 + 50;
            double errorRate = random.nextDouble() * 0.01;
            logger.info("平均响应时间: {} ms, 错误率: {}%", String.format("%.2f", avgResponseTime), String.format("%.4f", errorRate * 100));
            
            // 4. 保存统计结果
            logger.info("正在保存统计结果...");
            TimeUnit.MILLISECONDS.sleep(500);
            
            String summary = String.format("用户: %d/%d, 订单: %d/%.2f元, 响应: %.2fms, 错误率: %.4f%%",
                    activeUsers, totalUsers, totalOrders, totalAmount, avgResponseTime, errorRate * 100);
            logger.info("统计完成: {}", summary);
            XxlJobHelper.handleSuccess(summary);
            
        } catch (Exception e) {
            logger.error("系统数据统计任务执行失败", e);
            XxlJobHelper.handleFail("任务失败: " + e.getMessage());
        }
        
        logger.info("========== 系统数据统计任务完成 ==========");
    }

    /**
     * 缓存预热任务
     * 在系统低峰期预加载热点数据到缓存
     */
    @XxlJob("cacheWarmupHandler")
    public void cacheWarmupHandler() {
        logger.info("========== 缓存预热任务开始 ==========");
        
        try {
            // 1. 预热商品信息
            logger.info("正在预热商品信息缓存...");
            TimeUnit.SECONDS.sleep(1);
            int productCount = random.nextInt(100) + 50;
            logger.info("已预热 {} 个商品信息", productCount);
            
            // 2. 预热用户信息
            logger.info("正在预热用户信息缓存...");
            TimeUnit.SECONDS.sleep(1);
            int userCount = random.nextInt(200) + 100;
            logger.info("已预热 {} 个用户信息", userCount);
            
            // 3. 预热配置信息
            logger.info("正在预热系统配置缓存...");
            TimeUnit.MILLISECONDS.sleep(500);
            logger.info("已预热系统配置信息");
            
            String summary = String.format("预热完成: 商品 %d, 用户 %d", productCount, userCount);
            logger.info(summary);
            XxlJobHelper.handleSuccess(summary);
            
        } catch (Exception e) {
            logger.error("缓存预热任务执行失败", e);
            XxlJobHelper.handleFail("任务失败: " + e.getMessage());
        }
        
        logger.info("========== 缓存预热任务完成 ==========");
    }

    /**
     * 数据同步任务
     * 定时将数据同步到其他系统
     */
    @XxlJob("dataSyncHandler")
    public void dataSyncHandler() {
        logger.info("========== 数据同步任务开始 ==========");
        
        // 获取分片参数，支持分片处理大量数据
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        logger.info("分片信息: 当前分片 {}/{}", shardIndex, shardTotal);
        
        try {
            // 1. 查询需要同步的数据
            logger.info("正在查询需要同步的数据（分片 {}）...", shardIndex);
            TimeUnit.SECONDS.sleep(1);
            int dataCount = random.nextInt(100) + 50;
            logger.info("查询到 {} 条数据需要同步", dataCount);
            
            // 2. 同步数据
            int syncedCount = 0;
            int batchSize = 10;
            for (int i = 0; i < dataCount; i += batchSize) {
                int currentBatch = Math.min(batchSize, dataCount - i);
                logger.info("正在同步第 {} 批数据，共 {} 条...", (i / batchSize) + 1, currentBatch);
                TimeUnit.MILLISECONDS.sleep(300);
                syncedCount += currentBatch;
            }
            
            logger.info("数据同步完成，共同步 {} 条数据", syncedCount);
            XxlJobHelper.handleSuccess("分片 " + shardIndex + " 同步完成: " + syncedCount + " 条数据");
            
        } catch (Exception e) {
            logger.error("数据同步任务执行失败", e);
            XxlJobHelper.handleFail("任务失败: " + e.getMessage());
        }
        
        logger.info("========== 数据同步任务完成 ==========");
    }
}
