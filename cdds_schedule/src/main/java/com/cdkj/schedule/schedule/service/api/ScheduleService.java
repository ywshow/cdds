/**
 * project name:cdds
 * file name:ScheduleService
 * package name:com.cdkj.schedule.schedule.service.api
 * date:2018/8/27 13:44
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.service.api;

import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.schedule.pojo.Schedule;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;

import java.util.Map;

/**
 * description: 定时任务管理 <br>
 * date: 2018/8/27 13:44
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public interface ScheduleService extends BaseService {
    /**
     * description: 根据ID，查询定时任务 <br>
     *
     * @param jobId 主键
     * @return com.cdkj.model.schedule.pojo.Schedule
     */
    Schedule queryObject(String jobId);

    /**
     * description: 查询定时任务列表 <br>
     *
     * @param pageDTO 查询条件信息
     * @return com.cdkj.util.ResultInfo<com.cdkj.model.system.pojo.SysDict>
     */
    ResultInfo<Schedule> selectList(PageDTO pageDTO);

    /**
     * 查询总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * description: 保存定时任务 <br>
     *
     * @param schedule 对象信息
     * @return void
     */
    void save(Schedule schedule);

    /**
     * description: 更新定时任务 <br>
     *
     * @param schedule 对象信息
     * @return void
     */
    void update(Schedule schedule);

    /**
     * description: 批量删除定时任务 <br>
     *
     * @param jobIds 主键数组
     * @return void
     */
    void deleteBatch(String[] jobIds);

    /**
     * description: 批量更新定时任务状态 <br>
     *
     * @param jobIds 主键
     * @param status 状态
     * @return int
     */
    int updateBatch(String[] jobIds, Short status);

    /**
     * description: 立即执行 <br>
     *
     * @param jobIds 主键
     * @return void
     */
    void run(String[] jobIds);

    /**
     * description: 暂停运行 <br>
     *
     * @param jobIds 主键
     * @return void
     */
    void pause(String[] jobIds);

    /**
     * description: 恢复运行 <br>
     *
     * @param jobIds 主键
     * @return void
     */
    void resume(String[] jobIds);
}