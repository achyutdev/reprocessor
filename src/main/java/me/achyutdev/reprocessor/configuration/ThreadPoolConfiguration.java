package me.achyutdev.reprocessor.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
public class ThreadPoolConfiguration {

    @Value("${reprocessor.thread.name.prefix}")
    private String threadNamePrefix;

    @Value("${reprocessor.thread.pool.size}")
    private int threadPoolSize;

    @Bean("threadPool")
    ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(threadPoolSize);
        taskExecutor.setThreadNamePrefix(threadNamePrefix);
        return taskExecutor;
    }
}
