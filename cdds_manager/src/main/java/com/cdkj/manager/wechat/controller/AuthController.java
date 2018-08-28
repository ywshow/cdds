/**
 * project name:platform
 * file name:AuthController
 * package name:com.cdkj.wechat.controller
 * date:2017/12/7 下午3:55
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.manager.wechat.service.api.WeChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * description: 授权控制器 <br>
 * date: 2017/12/7 下午3:55
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@RequestMapping("/wechat/auth")
@Controller
public class AuthController extends BaseController {

    @Resource
    private WeChatService weChatService;

    /**
     * 获取微信第三方的授权URL地址
     *
     * @return 授权URL
     */
    @GetMapping(value = "/open/url")
    @ResponseBody
    String getAuthUrl() {
        return weChatService.getUrlWeChatAuth();
    }
}
