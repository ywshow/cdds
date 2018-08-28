/**
 * project name:saas
 * file name:SysUserService
 * package name:com.cdkj.system.service.api
 * date:2018/2/9 下午2:34
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysUser;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

    /**
     * description: 修改用户信息，只修改不为空的字段信息 <br>
     *
     * @param sysUser 用户信息
     * @return int
     */
    int updateCustomerWhereParamNotNull(SysUser sysUser) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 保存或修改用户
     * 当传入对象无id时候,执行Insert 操作 否则Update操作
     *
     * @param sysUser 用户对象
     * @return
     */
    int merge(SysUser sysUser);

    /**
     * 批量删除
     *
     * @param ids
     * @return 操作影响的行数
     */
    int batchRemove(List<String> ids);

    /**
     * 修改密码
     *
     * @param sysUser
     * @return
     */
    public int updatePwd(SysUser sysUser);
}