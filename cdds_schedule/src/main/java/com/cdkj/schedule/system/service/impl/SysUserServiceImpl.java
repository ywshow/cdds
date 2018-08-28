/**
 * project name:saas
 * file name:SysUserServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018/2/9 下午2:36
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.model.system.pojo.SysUser;
import com.cdkj.schedule.system.dao.SysUserMapper;
import com.cdkj.schedule.system.service.api.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * description: 系统用户管理 <br>
 * date: 2018/2/9 下午2:36
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, String> implements SysUserService {
    /**
     * 用户-部门关系表中套账号,暂先用
     */
    private final String sysAccount = "All";
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    /**
     * 根据用户名及登录源获取用户信息
     *
     * @param username    用户名
     * @param sourceLogin 登录源：0:APP登录，1:后端登录
     * @return 用户信息
     */
    @Override
    public SysUser selectByUsernameAndSourceLogin(String username, int sourceLogin) {
        if (StringUtils.isEmpty(username)) {
            throw new CustException(ErrorCode.ERROR_20001, "用户名为空");
        }
        return sysUserMapper.selectByUsernameAndSourceLogin(username, sourceLogin);
    }

    /**
     * 根据用户名及登录源获取用户信息
     *
     * @param mobile      手机号
     * @param sourceLogin 登录源：0:APP登录，1:后端登录
     * @return 用户信息
     */
    @Override
    public SysUser selectByMobileAndSourceLogin(String mobile, int sourceLogin) {
        if (StringUtils.isEmpty(mobile)) {
            throw new CustException(ErrorCode.ERROR_20001, "手机号为空");
        }
        return sysUserMapper.selectByMobileAndSourceLogin(mobile, sourceLogin);
    }

}