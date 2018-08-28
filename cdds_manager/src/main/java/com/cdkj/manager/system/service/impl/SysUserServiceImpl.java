/**
 * project name:saas
 * file name:SysUserServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018/2/9 下午2:36
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.*;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.system.dao.SysUserDeptMapper;
import com.cdkj.manager.system.dao.SysUserMapper;
import com.cdkj.manager.system.dao.SysUserRoleMapper;
import com.cdkj.manager.system.service.api.SysUserService;
import com.cdkj.model.system.pojo.SysUser;
import com.cdkj.model.system.pojo.SysUserDept;
import com.cdkj.model.system.pojo.SysUserRole;
import com.cdkj.manager.util.ShiroUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
    @Resource
    private SysUserDeptMapper sysUserDeptMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

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

    /**
     * description: 修改用户信息，只修改不为空的字段信息 <br>
     *
     * @param sysUser 客户信息
     * @return int
     */
    @Override
    public int updateCustomerWhereParamNotNull(SysUser sysUser) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustException(ErrorCode.ERROR_20001, "用户信息为空");
        }
        if (StringUtils.isEmpty(sysUser.getId())) {
            throw new CustException(ErrorCode.ERROR_20001, "用户ID为空");
        }
        SysUser tmpSysUser = new SysUser();
        PropertyUtils.copyProperties(tmpSysUser, sysUser);
        return sysUserMapper.updateByPrimaryKeySelective(tmpSysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int merge(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getId())) {
            String userId = UUIDGenerator.randomUUID();
            sysUser.setCreateDt(DateUtil.getNow());
            sysUser.setCreateBy(ShiroUtils.getUser().getUsername());
            sysUser.setId(userId);
            if (StringUtil.isEmpty(sysUser.getSysAccount())) {
                sysUser.setSysAccount(sysAccount);
            }
            sysUser.setSalt(userId);
            sysUser.setPassword(ShiroMd5Utils.encrypt(sysUser.getUsername(), sysUser.getPassword(), sysUser.getSalt()));
            int saveRow = this.sysUserMapper.insertSelective(sysUser);
            saveRow += saveUserDept(sysUser);
            saveRow += saveUserRole(sysUser);
            return saveRow;
        } else {
            sysUser.setUpdateDt(DateUtil.getNow());
            sysUser.setUpdateBy(ShiroUtils.getUser().getUsername());
            if (!StringUtils.isEmpty(sysUser.getPassword())) {
                sysUser.setPassword(ShiroMd5Utils.encrypt(ShiroMd5Utils.encrypt(sysUser.getUsername(), sysUser.getPassword(), sysUser.getSalt())));
            }
            int updateRow = this.updateByPrimaryKeySelective(sysUser);
            updateRow += saveUserRole(sysUser);
            updateRow += saveUserDept(sysUser);
            return updateRow;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePwd(SysUser sysUser) {
        sysUser.setUpdateDt(DateUtil.getNow());
        sysUser.setUpdateBy(ShiroUtils.getUser().getUsername());
        sysUser.setPassword(ShiroMd5Utils.encrypt(ShiroMd5Utils.encrypt(sysUser.getUsername(), sysUser.getPassword(), sysUser.getSalt())));
        return this.updateByPrimaryKeySelective(sysUser);
    }


    /**
     * 保存:用户-部门关联关系
     *
     * @param sysUser
     * @return
     */
    private int saveUserDept(SysUser sysUser) {
        /**
         * 删除原有关系
         */
        this.sysUserDeptMapper.deleteByUserId(sysUser.getId());
        SysUserDept sysUserDept = new SysUserDept();
        sysUserDept.setId(UUIDGenerator.randomUUID());
        sysUserDept.setCreateDt(DateUtil.getNow());
        sysUserDept.setCreateBy(sysUser.getCreateBy());
        sysUserDept.setUserId(sysUser.getId());
        sysUserDept.setDeptId(sysUser.getSysDept().getId());
        sysUserDept.setSysAccount(sysAccount);
        return sysUserDeptMapper.insertSelective(sysUserDept);
    }

    /**
     * 保存:用户-角色关联关系
     *
     * @param sysUser
     * @return
     */
    private int saveUserRole(SysUser sysUser) {
        /**
         * 删除原有关系
         */
        this.sysUserRoleMapper.deleteByUserId(sysUser.getId());
        int saveRow = 0;
        for (int i = 0; i < sysUser.getRoleIds().size(); i++) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setId(UUIDGenerator.randomUUID());
            sysUserRole.setCreateDt(DateUtil.getNow());
            sysUserRole.setCreateBy(sysUser.getCreateBy());
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(sysUser.getRoleIds().get(i));
            sysUserRole.setSysAccount(sysAccount);
            saveRow += sysUserRoleMapper.insertSelective(sysUserRole);
        }
        return saveRow;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchRemove(List<String> ids) {
        int updateRow = 0;
        for (int i = 0; i < ids.size(); i++) {
            SysUser sysUser = new SysUser();
            sysUser.setEnabled((short) 0);
            sysUser.setUpdateDt(DateUtil.getNow());
            sysUser.setId(ids.get(i));
            updateRow += this.updateByPrimaryKeySelective(sysUser);
        }
        return updateRow;
    }
}