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
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

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
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //延时启动
        schedulerFactoryBean.setStartupDelay(30);
        //启动时更新己存在的Job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //设置自动启动
        schedulerFactoryBean.setAutoStartup(true);
        //设置spring在quartz中上下文，已key/value形式
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        schedulerFactoryBean.setQuartzProperties(initProperties());
        return schedulerFactoryBean;
    }

    /**
     * description:初始化配置 <br>
     *
     * @return java.util.Properties
     */
    public Properties initProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("config/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * description: 注入Scheduler <br>
     *
     * @return org.quartz.Scheduler
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

}