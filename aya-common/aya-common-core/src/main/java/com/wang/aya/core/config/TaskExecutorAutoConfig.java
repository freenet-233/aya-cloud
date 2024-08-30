package com.wang.aya.core.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.wang.aya.common.i18.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.wang.aya.common.i18.common.constant.StringConstant.TRUE;

/**
 * 异步配置
 *
 * @author wangguangpeng
 * @date 2024/08/29 14:36
 **/
@EnableAsync
@AutoConfiguration
@RequiredArgsConstructor
public class TaskExecutorAutoConfig {

    /**
     * thread pool name
     */
    public static final String THREAD_POOL_TASK_EXECUTOR_NAME = "executor";

    /**
     * virtual thread switch
     */
    private static final String THREAD_VIRTUAL_ENABLED = "spring.threads.virtual.enabled";


    @Bean
    public Executor workStealingPoolExecutor(SpringTaskExecutionProperties springTaskExecutionProperties) {
        return TtlExecutors.getTtlExecutorService(
                Executors.newWorkStealingPool(springTaskExecutionProperties.getForkJoinPool().getCoreSize()));
    }

    @Bean(value = THREAD_POOL_TASK_EXECUTOR_NAME)
    public Executor executor(SpringTaskExecutionProperties springTaskExecutionProperties, Environment environment) {
        String threadNamePrefix = springTaskExecutionProperties.getThreadNamePrefix();
        String enabled = environment.getProperty(THREAD_VIRTUAL_ENABLED);
        if (ObjectUtil.equals(TRUE, enabled)) {
            // 虚拟线程
            return TtlExecutors.getTtlExecutor(new VirtualThreadTaskExecutor(threadNamePrefix));
        }
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心池大小
        executor.setCorePoolSize(springTaskExecutionProperties.getPool().getCoreSize());
        // 最大线程数
        executor.setMaxPoolSize(springTaskExecutionProperties.getPool().getMaxSize());
        // 队列容量
        executor.setQueueCapacity(springTaskExecutionProperties.getPool().getQueueCapacity());
        executor.setThreadPriority(Thread.MAX_PRIORITY);
        executor.setDaemon(false);
        executor.setAllowCoreThreadTimeOut(springTaskExecutionProperties.getPool().isAllowCoreThreadTimeout());
        // 线程空闲时间
        executor.setKeepAliveSeconds((int) springTaskExecutionProperties.getPool().getKeepAlive().toSeconds());
        // 线程名字前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        // 初始化
        executor.initialize();
        return TtlExecutors.getTtlExecutorService(executor.getThreadPoolExecutor());

    }

}
