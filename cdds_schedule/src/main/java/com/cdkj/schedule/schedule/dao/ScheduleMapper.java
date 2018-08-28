/**
 * project name:cdds
 * file name:ScheduleMapper
 * package name:com.cdkj.schedule.schedule.dao
 * date:2018/8/27 13:52
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.schedule.pojo.Schedule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * description: 定时任务 <br>
 * date: 2018/8/27 13:52
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Component
public interface ScheduleMapper extends BaseMapper<Schedule, String> {

    /**
     * description: 条件查询 <br>
     *
     * @param params map对象
     * @return java.util.List<com.cdkj.model.schedule.pojo.Schedule>
     */
    List<Schedule> selectList(@Param(value = "params") Map<String, Object> params);
}