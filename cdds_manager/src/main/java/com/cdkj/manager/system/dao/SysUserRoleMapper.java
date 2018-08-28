package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface SysUserRoleMapper extends BaseMapper<SysUserRole, String> {
    /**
     * 通过角色ID删除所有关联的用户角色关系
     *
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(@Param(value = "roleId") String roleId);

    /**
     * 通过用户ID删除所有关联的用户角色关系
     *
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") String userId);
}