/**
 * project name:saas
 * file name:LoginAppService
 * package name:com.cdkj.system.service.api
 * date:2018/3/26 17:39
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysUser;

import java.util.Map;

/**
 * description: app登录 <br>
 * date: 2018/3/26 17:39
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public interface LoginAppService extends BaseService<SysUser, String> {

    /**
     * description: 登录 <br>
     *
     * @param loginAccount 手机/账号
     * @param code         验证码
     * @param password     密码
     * @param loginModel   登录模式0：手机登录，1：账号密码登录
     * @return 返回登录结果
     */
    Map<String, Object> loginApp(String loginAccount, String code, String password, int loginModel, String token);
}