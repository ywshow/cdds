/**
 * project name:saas
 * file name:LoginAppServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018/3/26 17:40
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.constant.ResultCode;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.constant.RedisKeys;
import com.cdkj.constant.SysDictConstants;
import com.cdkj.manager.msg.service.api.MsgIdentifyCodeService;
import com.cdkj.manager.system.service.api.LoginAppService;
import com.cdkj.manager.system.service.api.SysUserService;
import com.cdkj.manager.system.service.api.UsrCustomerService;
import com.cdkj.model.system.pojo.SysUser;
import com.cdkj.util.DateUtil;
import com.cdkj.util.ShiroMd5Utils;
import com.cdkj.util.UUIDGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * description: app登录 <br>
 * date: 2018/3/26 17:40
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class LoginAppServiceImpl extends BaseServiceImpl<SysUser, String> implements LoginAppService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private MsgIdentifyCodeService msgIdentifyCodeService;

    @Resource
    private UsrCustomerService usrCustomerService;

    /**
     * description: 登录 <br>
     *
     * @param loginAccount 手机/账号
     * @param code         验证码
     * @param password     密码
     * @param loginModel   登录模式0：手机登录，1：账号密码登录
     * @return 返回登录结果
     */
    @Override
    public Map<String, Object> loginApp(String loginAccount, String code, String password, int loginModel, String token) {
        SysUser user = new SysUser();
        if (!StringUtils.isEmpty(token)) {
            /**token存在且有效，则返回用户信息**/
            //token校验
            String redisToken = redisClient.get(RedisKeys.LOGIN_APP_TOKEN + token);
            if (StringUtils.isEmpty(redisToken)) {
                throw new CustException(ErrorCode.ERROR_20009, "token失效");
            }
            switch (loginModel) {
                case SysDictConstants.LOGINAPP.LOGIN_MODEL_MOBILE:
                    // 手机登录
                    user = sysUserService.selectByMobileAndSourceLogin(loginAccount, SysDictConstants.LOGINAPP.SOURCE_LOGIN_APP);
                    break;
                case SysDictConstants.LOGINAPP.LOGIN_MODEL_ACCOUNT:
                    // 账户密码登录
                    user = sysUserService.selectByUsernameAndSourceLogin(loginAccount, SysDictConstants.LOGINAPP.SOURCE_LOGIN_APP);
                    break;
                default:
                    break;
            }
        } else {
            switch (loginModel) {
                case SysDictConstants.LOGINAPP.LOGIN_MODEL_MOBILE:
                    // 手机登录
                    user = loginByMobile(loginAccount, code, token);
                    break;
                case SysDictConstants.LOGINAPP.LOGIN_MODEL_ACCOUNT:
                    // 账户密码登录
                    user = loginByUserName(loginAccount, password, token);
                    break;
                default:
                    break;
            }
        }

        usrCustomerService.updateUserIdByMobile(user.getId(), user.getMobile());

        Map<String, Object> map = new HashMap<>(2);
        String newToken = UUIDGenerator.randomUUID();
        map.put("user", user);
        map.put("token", newToken);
        redisClient.set(RedisKeys.LOGIN_APP_TOKEN + newToken, newToken, RedisKeys.LOGIN_APP_TOKEN_VALID_TIME);
        return map;
    }

    /**
     * description: 手机登录 <br>
     *
     * @param mobile 手机
     * @param code   验证码
     * @return 返回登录用户信息
     */
    public SysUser loginByMobile(String mobile, String code, String token) {
        if (StringUtils.isEmpty(mobile)) {
            throw new CustException(ErrorCode.ERROR_20001, "手机号为空");
        }
        if (StringUtils.isEmpty(code)) {
            throw new CustException(ErrorCode.ERROR_20001, "验证码为空");
        }
        //测试号码
        String tmpMobile = "15717506403";
        if (!mobile.equals(tmpMobile)) {
            boolean valid = msgIdentifyCodeService.checkIdentifyCode(null, mobile, code);
            if (!valid) {
                throw new CustException(ErrorCode.ERROR_20088, "验证码错误");
            }
        }

        SysUser sysUser = sysUserService.selectByMobileAndSourceLogin(mobile, SysDictConstants.LOGINAPP.SOURCE_LOGIN_APP);
        if (ObjectUtils.isEmpty(sysUser)) {
            sysUser = new SysUser();
            initLoginSysUser(sysUser, mobile);
        }
        return sysUser;
    }

    /***
     * description: 新用户登录，初始化登录信息 <br>
     *
     * @param sysUser 系统用户信息
     * @param mobile 手机号
     * @return void
     */
    public void initLoginSysUser(SysUser sysUser, String mobile) {
        sysUser.setId(UUIDGenerator.randomUUID());
        sysUser.setUsername(mobile);
        sysUser.setMobile(mobile);
        sysUser.setSysAccount("ALL");
        sysUser.setSalt(UUIDGenerator.randomUUID());
        sysUser.setPassword("-1");
        sysUser.setLastLoginDt(DateUtil.getNow());
        sysUser.setSourceLogin(SysDictConstants.LOGINAPP.SOURCE_LOGIN_APP);
        sysUser.setCreateDt(DateUtil.getNow());
        sysUserService.insertSelective(sysUser);
    }

    /**
     * description: 账号密码登录 <br>
     *
     * @param userName 账户名
     * @param password 密码
     * @return 返回登录用户信息
     */
    public SysUser loginByUserName(String userName, String password, String token) {
        if (StringUtils.isEmpty(userName)) {
            throw new CustException(ErrorCode.ERROR_20001, "账号为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new CustException(ErrorCode.ERROR_20001, "密码为空");
        }
        SysUser sysUser = sysUserService.selectByUsernameAndSourceLogin(userName, SysDictConstants.LOGINAPP.SOURCE_LOGIN_APP);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustException(ResultCode.NO_DATA, "无相关用户信息");
        }

        password = ShiroMd5Utils.encrypt(userName, password, sysUser.getSalt());
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //一周
            subject.getSession().setTimeout(604800000);
            subject.login(usernamePasswordToken);
            subject.getSession().setAttribute("currentUser", sysUser);
        } catch (AuthenticationException e) {
            throw new CustException(ErrorCode.ERROR_20002, "用户名或者密码错误！");
        }
        return sysUser;
    }
}