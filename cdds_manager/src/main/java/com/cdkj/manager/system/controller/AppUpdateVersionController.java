/**
 * project name:saas
 * file name:AppUpdateVersionController
 * package name:com.cdkj.system.controller
 * date:2018/4/2 14:40
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.JsonUtils;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.system.service.api.AppUpdateVersionService;
import com.cdkj.model.system.pojo.AppUpdateVersion;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description: app版本管理 <br>
 * date: 2018/4/2 14:40
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/app/open/version/")
public class AppUpdateVersionController extends BaseController<AppUpdateVersion> {

    @Resource
    private AppUpdateVersionService appUpdateVersionService;

    /**
     * description: 根据条件查询，无则查询全部 <br>
     *
     * @param model 对象信息(可空)
     * @return 返回数据信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "对象信息(可空)", dataType = "AppUpdateVersion")
    })
    @GetMapping("select/condition")
    public String selectByCondition(AppUpdateVersion model) {
        try {
            return JsonUtils.res(appUpdateVersionService.selectByCondition(model));
        } catch (CustException ce) {
            logger.error("AppUpdateVersionController.selectByCondition()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("AppUpdateVersionController.selectByCondition()方法系统异常!error={}", e);
            return JsonUtils.resFailed(206, ErrorCode.ERROR_20002, "02", "系统异常");
        }
    }
}