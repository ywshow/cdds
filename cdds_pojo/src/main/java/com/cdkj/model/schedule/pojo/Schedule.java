/**
 * project name:cdds
 * file name:Schedule
 * package name:com.cdkj.model.schedule
 * date:2018/8/27 13:49
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.model.schedule.pojo;

import com.cdkj.common.base.model.pojo.BaseModel;

/**
 * description: 定时任务管理 <br>
 * date: 2018/8/27 13:49
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public class Schedule extends BaseModel {
    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * spring bean名称
     */
    private String beanName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * cron表达式
     */
    private String cronExpression;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}