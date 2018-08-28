/**
 * project name:saas
 * file name:SysRoleServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018/3/2 上午10:51
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.util.DateUtil;
import com.cdkj.manager.system.dao.SysRoleMapper;
import com.cdkj.manager.system.dao.SysRolePermissionMapper;
import com.cdkj.manager.system.dao.SysUserRoleMapper;
import com.cdkj.manager.system.service.api.SysRoleService;
import com.cdkj.model.system.pojo.SysRole;
import com.cdkj.model.system.pojo.SysRolePermission;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.util.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: 角色管理接口实现 <br>
 * date: 2018/3/2 上午10:51
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, String> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    private int saveRolePermission(SysRole sysRole) {
        //如果存在原有关系，则删除
        int updateTag = 0;
        sysRolePermissionMapper.deleteByRoleId(sysRole.getId());
        for (String permissionId : sysRole.getPermissionIds()) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setId(UUIDGenerator.randomUUID());
            sysRolePermission.setSysAccount(ShiroUtils.getUser().getSysAccount());
            sysRolePermission.setCreateBy(ShiroUtils.getUser().getUsername());
            sysRolePermission.setCreateDt(DateUtil.getNow());
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(sysRole.getId());
            updateTag += sysRolePermissionMapper.insertSelective(sysRolePermission);
        }
        return updateTag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertWithPermission(SysRole sysRole) {
        if (StringUtils.isEmpty(sysRole.getId())) {
            sysRole.setId(UUIDGenerator.randomUUID());
            sysRole.setSysAccount(ShiroUtils.getUser().getSysAccount());
            sysRole.setCreateBy(ShiroUtils.getUser().getUsername());
            sysRole.setCreateDt(DateUtil.getNow());
        }
        int saveTag = sysRoleMapper.insertSelective(sysRole);
        if (saveTag > 0) {
            saveTag += saveRolePermission(sysRole);
        }
        return saveTag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateWithPermission(SysRole sysRole) {
        int saveTag = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        if (saveTag > 0) {
            saveTag += saveRolePermission(sysRole);
        }
        return saveTag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int remove(String roleId) {
        //删除所有的关联记录
        sysUserRoleMapper.deleteByRoleId(roleId);
        sysRolePermissionMapper.deleteByRoleId(roleId);
        return sysRoleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public List<SysRole> selectByUserId(String userId) {
        return sysRoleMapper.selectByUserId(userId);
    }


}