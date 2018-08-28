package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission, String> {
    /**
     * 通过角色ID获取权限清单
     *
     * @param roleId 角色ID
     * @return 权限清单
     */
    List<SysRolePermission> getByRoleId(@Param(value = "roleId") String roleId);

    /**
     * 通过角色ID删除权限清单
     *
     * @param roleId 角色ID
     * @return 删除结果
     */
    int deleteByRoleId(@Param(value = "roleId") String roleId);

}