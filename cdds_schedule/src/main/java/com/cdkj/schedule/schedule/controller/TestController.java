/**
 * project name:cdds
 * file name:TestController
 * package name:com.cdkj.schedule.schedule.controller
 * date:2018/9/5 17:01
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.schedule.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.model.schedule.pojo.Schedule;
import com.cdkj.schedule.schedule.service.api.TestService;
import com.cdkj.util.JsonUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * description: //TODO <br>
 * date: 2018/9/5 17:01
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
public class TestController extends BaseController {

    @Resource
    private TestService testService;

    public void test() {
        testService.test();
    }

    public void info() {
        testService.info();
    }

    public String save(String json) {
        try {
            testService.save(JsonUtils.toObject("{"+json+"}", Schedule.class));
            return JsonUtils.res("");
        } catch (CustException ce) {
            logger.error("ScheduleController.save()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("ScheduleController.save()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, ErrorCode.ERROR_20002, "03", "系统异常");
        }
    }

    public void tmp(){
        System.out.println("呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜");
    }

    public void sss(){
        System.out.println("lllllllllllllllllllllllllllllllllll");
    }
}