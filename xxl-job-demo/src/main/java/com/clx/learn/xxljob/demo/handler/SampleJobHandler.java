package com.clx.learn.xxljob.demo.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 示例任务处理器
 * 演示各种类型的 XXL-Job 任务
 */
@Component
public class SampleJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(SampleJobHandler.class);

    /**
     * 1. 简单任务示例
     * 最基础的任务类型，直接执行业务逻辑
     */
    @XxlJob("simpleJobHandler")
    public void simpleJobHandler() {
        logger.info("====================简单任务开始执行====================");
        logger.info("执行时间: {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        // 模拟业务处理
        try {
            TimeUnit.SECONDS.sleep(2);
            logger.info("业务处理完成");
        } catch (InterruptedException e) {
            logger.error("任务执行异常", e);
        }
        
        logger.info("====================简单任务执行完成====================");
    }

    /**
     * 2. 带参数的任务示例
     * 可以在调度中心配置任务参数，在任务中获取并使用
     */
    @XxlJob("paramJobHandler")
    public void paramJobHandler() {
        logger.info("====================带参数任务开始执行====================");
        
        // 获取任务参数
        String param = XxlJobHelper.getJobParam();
        logger.info("任务参数: {}", param);
        
        // 根据参数执行不同的业务逻辑
        if (param != null && !param.isEmpty()) {
            logger.info("处理参数: {}", param);
            // 实际业务处理
        } else {
            logger.warn("未传入任务参数");
        }
        
        logger.info("====================带参数任务执行完成====================");
    }

    /**
     * 3. 分片任务示例
     * 支持任务分片，适用于大数据量处理场景
     * 可以将一个任务拆分成多个分片，由多个执行器并行处理
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() {
        logger.info("====================分片任务开始执行====================");
        
        // 获取分片参数
        int shardIndex = XxlJobHelper.getShardIndex();  // 当前分片序号（从0开始）
        int shardTotal = XxlJobHelper.getShardTotal();  // 总分片数
        
        logger.info("分片信息: 当前分片序号={}, 总分片数={}", shardIndex, shardTotal);
        
        // 根据分片参数处理数据
        // 例如：查询数据库时，可以根据 id % shardTotal == shardIndex 来分片
        logger.info("处理当前分片的数据...");
        
        // 模拟分片数据处理
        for (int i = 0; i < 10; i++) {
            if (i % shardTotal == shardIndex) {
                logger.info("处理数据项: {}", i);
            }
        }
        
        logger.info("====================分片任务执行完成====================");
    }

    /**
     * 4. 返回值任务示例
     * 可以通过 XxlJobHelper 设置任务执行结果，在调度中心查看
     */
    @XxlJob("resultJobHandler")
    public void resultJobHandler() {
        logger.info("====================返回值任务开始执行====================");
        
        try {
            // 执行业务逻辑
            int processedCount = 100;
            logger.info("处理了 {} 条数据", processedCount);
            
            // 设置任务执行结果（可选）
            XxlJobHelper.handleSuccess("成功处理了 " + processedCount + " 条数据");
            
        } catch (Exception e) {
            logger.error("任务执行失败", e);
            // 设置失败结果
            XxlJobHelper.handleFail("任务执行失败: " + e.getMessage());
        }
        
        logger.info("====================返回值任务执行完成====================");
    }

    /**
     * 5. 定时报表任务示例
     * 模拟生成每日报表的场景
     */
    @XxlJob("dailyReportJobHandler")
    public void dailyReportJobHandler() {
        logger.info("====================定时报表任务开始执行====================");
        
        String reportDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        logger.info("生成日期: {}", reportDate);
        
        try {
            // 1. 统计数据
            logger.info("正在统计数据...");
            TimeUnit.SECONDS.sleep(1);
            
            // 2. 生成报表
            logger.info("正在生成报表...");
            TimeUnit.SECONDS.sleep(1);
            
            // 3. 发送报表
            logger.info("正在发送报表...");
            TimeUnit.SECONDS.sleep(1);
            
            logger.info("报表生成并发送成功");
            XxlJobHelper.handleSuccess("报表生成成功: " + reportDate);
            
        } catch (Exception e) {
            logger.error("报表生成失败", e);
            XxlJobHelper.handleFail("报表生成失败: " + e.getMessage());
        }
        
        logger.info("====================定时报表任务执行完成====================");
    }

    /**
     * 6. 数据清理任务示例
     * 模拟清理过期数据的场景
     */
    @XxlJob("dataCleanupJobHandler")
    public void dataCleanupJobHandler() {
        logger.info("====================数据清理任务开始执行====================");
        
        // 获取清理天数参数（默认30天）
        String param = XxlJobHelper.getJobParam();
        int days = 30;
        if (param != null && !param.isEmpty()) {
            try {
                days = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                logger.warn("参数格式错误，使用默认值: {}", days);
            }
        }
        
        logger.info("清理 {} 天前的数据", days);
        
        try {
            // 模拟清理操作
            int deletedCount = 0;
            for (int i = 0; i < 5; i++) {
                logger.info("正在清理第 {} 批数据...", i + 1);
                TimeUnit.MILLISECONDS.sleep(500);
                deletedCount += 20;
            }
            
            logger.info("清理完成，共删除 {} 条数据", deletedCount);
            XxlJobHelper.handleSuccess("清理完成，共删除 " + deletedCount + " 条数据");
            
        } catch (Exception e) {
            logger.error("数据清理失败", e);
            XxlJobHelper.handleFail("数据清理失败: " + e.getMessage());
        }
        
        logger.info("====================数据清理任务执行完成====================");
    }
}
