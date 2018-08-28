package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysPermissionMapper extends BaseMapper<SysPermission, String> {
    /**
     * 获取用户权限字符串集合
     * @param userId 用户
     * @return 权限集
     */
    List<String> listUserPerms(@Param(value = "userId") String userId);

    /**
     * 查询用户的权限清单
     *
     * @param userId 用户ID
     * @return 权限清单
     */
    List<SysPermission> listUserPermission(@Param(value = "userId") String userId);

    /**
     * 查询角色的权限清单
     *
     * @param roleId 角色ID
     * @return 权限清单
     */
    List<String> listRoleIdPermission(@Param(value = "roleId") String roleId);

    /**
     * 查询所有的有效权限字符串
     *
     * @return 权限清单
     */
    List<String> listAllPerms();

    /**
     * 查询所有的有效权限
     *
     * @return 权限清单
     */
    List<SysPermission> listAllPermission();

}