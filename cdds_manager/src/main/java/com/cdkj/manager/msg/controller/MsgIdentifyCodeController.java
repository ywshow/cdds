/**
 * project name:saas
 * file name:MsgIdentifyCodeController
 * package name:com.cdkj.msg.controller
 * date:2018/3/20 上午9:01
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.msg.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.StringUtil;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.msg.service.api.MsgIdentifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description: 验证码短信控制器 <br>
 * date: 2018/3/20 上午9:01
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Api(value = "/msg/identify", description = "验证码短信控制器")
@RestController
@RequestMapping("/msg/identify")
public class MsgIdentifyCodeController extends BaseController {
    @Resource
    private MsgIdentifyCodeService msgIdentifyCodeService;


    @ApiOperation(value = "发送短信", notes = "发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysAccount", value = "帐套号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/open/send/{sysAccount}/{mobile}")
    public String send(@PathVariable("sysAccount") String sysAccount, @PathVariable("mobile") String mobile) {
        try {
            if (!StringUtil.areNotEmpty(sysAccount, mobile)) {
                throw new CustException(224, ErrorCode.ERROR_20001, "01", "参数异常");
            }
            return JsonUtils.toJson(msgIdentifyCodeService.send(sysAccount, mobile));
        } catch (CustException ce) {
            logger.error("MsgIdentifyCodeController.send()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("MsgIdentifyCodeController.send()方法系统异常!error={}", e);
            return JsonUtils.resFailed(224, ErrorCode.ERROR_20002, "01", "系统异常");
        }
    }

    @ApiOperation(value = "发送短信", notes = "发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysAccount", value = "帐套号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/open/send/{mobile}")
    public String sendByMobile(@PathVariable("mobile") String mobile) {
        try {
            if (!StringUtil.areNotEmpty(mobile)) {
                throw new CustException(224, ErrorCode.ERROR_20001, "01", "参数异常");
            }
            return JsonUtils.toJson(msgIdentifyCodeService.send(null, mobile));
        } catch (CustException ce) {
            logger.error("MsgIdentifyCodeController.sendByMobile()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("MsgIdentifyCodeController.sendByMobile()方法系统异常!error={}", e);
            return JsonUtils.resFailed(224, ErrorCode.ERROR_20002, "01", "系统异常");
        }
    }


}