/**
 * project name:cdds
 * file name:ScheduleController
 * package name:com.cdkj.schedule.schedule.controller
 * date:2018/8/27 13:43
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.model.schedule.pojo.Schedule;
import com.cdkj.model.system.pojo.SysDict;
import com.cdkj.schedule.schedule.service.api.ScheduleService;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;
import com.cdkj.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 定时任务管理 <br>
 * date: 2018/8/27 13:43
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sys/schedule/")
public class ScheduleController extends BaseController<Schedule> {
    @Autowired
    private ScheduleService scheduleService;

    private final String prefix = "page/schedule/";

    @RequestMapping("index")
    public String index() {
        return prefix + "index";
    }

    @RequestMapping("add")
    public String add() {
        return prefix + "add";
    }

    /**
     * 定时任务列表
     */
    @RequestMapping("list")
//    @RequiresPermissions("sys:schedule:list")
    @ResponseBody
    public String list(PageDTO<Schedule> pageDTO) {
        try {
            ResultInfo<Schedule> resultInfo = scheduleService.selectList(pageDTO);
            return JsonUtils.res(resultInfo);
        } catch (CustException ce) {
            logger.error("ScheduleController.list()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("ScheduleController.list()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "01", "系统异常");
        }
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("selectById/{jobId}")
//    @RequiresPermissions("sys:schedule:info")
    @ResponseBody
    public String selectById(@PathVariable("id") String id) {
        try {
            Schedule schedule = scheduleService.queryObject(id);
            return JsonUtils.res(schedule);
        } catch (CustException ce) {
            logger.error("ScheduleController.selectById()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("ScheduleController.selectById()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "02", "系统异常");
        }
    }

    /**
     * 保存定时任务
     */
    @RequestMapping("save")
//    @RequiresPermissions("sys:schedule:save")
    @ResponseBody
    public String save(Schedule schedule) {
        try {
            scheduleService.save(schedule);
            return JsonUtils.res(schedule);
        } catch (CustException ce) {
            logger.error("ScheduleController.save()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        }catch (Exception e) {
            logger.error("ScheduleController.save()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "03", "系统异常");
        }
    }

    /**
     * 修改定时任务
     */
    @RequestMapping("update")
//    @RequiresPermissions("sys:schedule:update")
    @ResponseBody
    public String update(Schedule schedule) {
        try {
            scheduleService.update(schedule);
            return JsonUtils.res(schedule);
        } catch (CustException ce) {
            logger.error("ScheduleController.update()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("ScheduleController.update()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "03", "系统异常");
        }
    }

    /**
     * 删除定时任务
     */
    @RequestMapping("delete")
//    @RequiresPermissions("sys:schedule:delete")
    @ResponseBody
    public String delete(String[] ids) {
        try {
            scheduleService.deleteBatch(ids);
            return JsonUtils.res(ids);
        } catch (CustException ce) {
            logger.error("ScheduleController.delete()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("ScheduleController.delete()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "04", "系统异常");
        }
    }

    /**
     * 立即执行任务
     */
    @RequestMapping("run")
//    @RequiresPermissions("sys:schedule:run")
    @ResponseBody
    public String run(String[] ids) {
        try {
            scheduleService.run(ids);
            return JsonUtils.res(ids);
        } catch (CustException ce) {
            logger.error("ScheduleController.run()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("ScheduleController.run()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "04", "系统异常");
        }
    }

    /**
     * 暂停定时任务
     */
    @RequestMapping("pause")
//    @RequiresPermissions("sys:schedule:pause")
    @ResponseBody
    public String pause(String[] ids) {
        try {
            scheduleService.pause(ids);
            return JsonUtils.res(ids);
        } catch (CustException ce) {
            logger.error("ScheduleController.pause()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("ScheduleController.pause()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "05", "系统异常");
        }
    }

    /**
     * 恢复定时任务
     */
    @RequestMapping("/resume")
//    @RequiresPermissions("sys:schedule:resume")
    @ResponseBody
    public String resume(String[] ids) {
        try {
//            scheduleService.resume(ids);
            return JsonUtils.res(ids);
        } catch (CustException ce) {
            logger.error("ScheduleController.resume()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("ScheduleController.resume()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "06", "系统异常");
        }
    }

}