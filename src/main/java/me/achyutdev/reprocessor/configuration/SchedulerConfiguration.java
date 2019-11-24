package me.achyutdev.reprocessor.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulerConfiguration {

    @Value("${reprocessor.thread.name.prefix}")
    private String threadNamePrefix;

    @Value("${reprocessor.thread.pool.size}")
    private int threadPoolSize;

    @Bean("threadPoolTaskScheduler")
    ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(threadPoolSize);
        taskScheduler.setThreadNamePrefix(threadNamePrefix);
        return taskScheduler;
    }
}
