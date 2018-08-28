/**
 * project name:saas
 * file name:LoginAppController
 * package name:com.cdkj.system.controller
 * date:2018/3/26 17:20
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.JsonUtils;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.system.service.api.LoginAppService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description: app登录 <br>
 * date: 2018/3/26 17:20
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/login/app/")
public class LoginAppController extends BaseController {

    @Resource
    private LoginAppService loginAppService;

    /**
     * description: 登录 <br>
     *
     * @param loginAccount 手机/账号
     * @param code         验证码
     * @param password     密码
     * @param loginModel   登录模式0：手机登录，1：账号密码登录
     * @return 返回登录结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginAccount", value = "手机", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", dataType = "string"),
            @ApiImplicitParam(name = "loginModel", value = "登录模式0：手机登录，1：账号密码登录", required = true, dataType = "int")
    })
    @GetMapping("open/loginApp")
    public String loginApp(String loginAccount, String code, String password, int loginModel, String token) {
        try {
            return JsonUtils.res(loginAppService.loginApp(loginAccount, code, password, loginModel,token));
        } catch (CustException e) {
            logger.error("LoginAppController.loginApp()方法异常!error={}", e);
            return JsonUtils.resFailed(e.getMsg());
        } catch (Exception e) {
            logger.error("LoginAppController.loginApp()方法系统异常!error={}", e);
            return JsonUtils.resFailed(302, ErrorCode.ERROR_20002, "01", "系统异常");
        }
    }
}