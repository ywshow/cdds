/**
 * project name:cdds
 * file name:ScheduleConfig
 * package name:com.cdkj.schedule.schedule.config
 * date:2018/8/27 14:26
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.config;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * description: 定时任务注入（非公用，写在当前项目） <br>
 * date: 2018/8/27 14:26
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Configuration
public class ScheduleConfig {

    /**
     * description: 注入SchedulerFactoryBean <br>
     *
     * @return org.springframework.scheduling.quartz.SchedulerFactoryBean
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

    /**
     * description: 注入Scheduler <br>
     *
     * @return org.quartz.Scheduler
     */
    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

}