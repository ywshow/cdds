/**
 * project name:cdds
 * file name:ScheduleServiceImpl
 * package name:com.cdkj.schedule.schedule.service.impl
 * date:2018/8/27 13:46
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.constant.Constant;
import com.cdkj.model.schedule.pojo.Schedule;
import com.cdkj.schedule.schedule.dao.ScheduleMapper;
import com.cdkj.schedule.schedule.service.api.ScheduleService;
import com.cdkj.schedule.util.ScheduleUtils;
import com.cdkj.schedule.util.ShiroUtils;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 定时任务管理 <br>
 * date: 2018/8/27 13:46
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class ScheduleServiceImpl extends BaseServiceImpl implements ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private Scheduler scheduler;

    /**
     * description: 初始化定时器 <br>
     *
     * @return void
     */
    @PostConstruct
    public void init() {
        List<Schedule> scheduleJobList = scheduleMapper.selectList(new HashMap<>(5));
        for (Schedule scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            // 如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    /**
     * description: 查询定时任务 <br>
     *
     * @param jobId 主键
     * @return com.cdkj.model.schedule.pojo.Schedule
     */
    @Override
    public Schedule queryObject(String jobId) {
        return scheduleMapper.selectByPrimaryKey(jobId);
    }

    /**
     * description: 查询定时任务列表 <br>
     *
     * @param pageDTO 查询条件
     * @return java.util.List<com.cdkj.model.schedule.pojo.Schedule>
     */
    @Override
    public ResultInfo<Schedule> selectList(PageDTO pageDTO) {
        //参数，查询时做校验，map可为空
        Map<String, Object> map = JsonUtils.getMaptoFromJson(pageDTO.getParams().toString());
        String key = "sysAccount";
        map.put(key, ShiroUtils.getUser().getSysAccount());
        Page page = PageHelper.startPage(pageDTO.getPageNumber(), pageDTO.getPageSize());
        List<Schedule> list = scheduleMapper.selectList(map);
        return new ResultInfo(page, list);
    }

    /**
     * 查询总数
     *
     * @param map
     */
    @Override
    public int queryTotal(Map<String, Object> map) {
        return 0;
    }

    /**
     * description: 保存定时任务 <br>
     *
     * @param schedule 对象信息
     * @return void
     */
    @Override
    public void save(Schedule schedule) {
        this.initDefaultPrpperty(ShiroUtils.getUserId(), schedule);
        schedule.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        scheduleMapper.insertSelective(schedule);
    }

    /**
     * description: 更新定时任务 <br>
     *
     * @param schedule 对象信息
     * @return void
     */
    @Override
    public void update(Schedule schedule) {
        ScheduleUtils.updateScheduleJob(scheduler, schedule);
        scheduleMapper.updateByPrimaryKeySelective(schedule);
    }

    /**
     * description: 批量删除定时任务 <br>
     *
     * @param jobIds 主键数组
     * @return void
     */
    @Override
    public void deleteBatch(String[] jobIds) {
        for (String id : jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler, id);
            scheduleMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * description: 批量更新定时任务状态 <br>
     *
     * @param jobIds 主键
     * @param status 状态
     * @return int
     */
    @Override
    public int updateBatch(String[] jobIds, int status) {
        return 0;
    }

    /**
     * description: 立即执行 <br>
     *
     * @param jobIds 主键数组
     * @return void
     */
    @Override
    public void run(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.run(scheduler, queryObject(jobId));
        }
    }

    /**
     * description: 暂停运行 <br>
     *
     * @param jobIds 主键数组
     * @return void
     */
    @Override
    public void pause(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }
        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

    /**
     * description: 恢复运行 <br>
     *
     * @param jobIds 主键数组
     * @return void
     */
    @Override
    public void resume(String[] jobIds) {
        for (String jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }
        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }
}