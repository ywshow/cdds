/**
 * project name:saas
 * file name:SysUserService
 * package name:com.cdkj.system.service.api
 * date:2018/2/9 下午2:34
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysUser;

/**
 * description: 用户管理服务 <br>
 * date: 2018/2/9 下午2:34
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysUserService extends BaseService<SysUser, String> {
    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser selectByUsername(String username);

    /**
     * 根据用户名及登录源获取用户信息
     *
     * @param username    用户名
     * @param sourceLogin 登录源：0:APP登录，1:后端登录
     * @return 用户信息
     */
    SysUser selectByUsernameAndSourceLogin(String username, int sourceLogin);

    /**
     * 根据用户名及登录源获取用户信息
     *
     * @param mobile      手机号
     * @param sourceLogin 登录源：0:APP登录，1:后端登录
     * @return 用户信息
     */
    SysUser selectByMobileAndSourceLogin(String mobile, int sourceLogin);
}