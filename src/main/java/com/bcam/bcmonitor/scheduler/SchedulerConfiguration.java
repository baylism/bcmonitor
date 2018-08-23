package com.bcam.bcmonitor.scheduler;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableAsync
@Configuration
@EnableScheduling
public class SchedulerConfiguration {
// public class SchedulerConfiguration {


    // @Override
    // public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    //     ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    //
    //     threadPoolTaskScheduler.setPoolSize(10);
    //     threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
    //     threadPoolTaskScheduler.initialize();
    //
    //     taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    // }
    //
    // // @Override
    // // public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    // //     taskRegistrar.setScheduler(taskExecutor());
    // // }
    // //
    // @Bean
    // public Executor taskExecutor() {
    //     return Executors.newScheduledThreadPool(50);
    // }

    // this one works
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        return taskScheduler;
    }

    // @Bean
    // public Executor taskScheduler() {
    //     return Executors.newScheduledThreadPool(5);
    // }


}