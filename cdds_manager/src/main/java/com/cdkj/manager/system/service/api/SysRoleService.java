/**
 * project name:saas
 * file name:SysRoleService
 * package name:com.cdkj.system.service.api
 * date:2018/3/2 上午10:50
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysRole;

import java.util.List;

/**
 * description:  角色管理接口 <br>
 * date: 2018/3/2 上午10:50
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysRoleService extends BaseService<SysRole, String> {
    /**
     * 保存并同步保存权限信息
     *
     * @param sysRole 角色信息
     * @return 保存影响行数
     */
    int insertWithPermission(SysRole sysRole);

    /**
     * 更新角色权限信息
     *
     * @param sysRole 角色信息
     * @return 影响行数
     */
    int updateWithPermission(SysRole sysRole);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 角色删除结果
     */
    int remove(String roleId);

    /**
     * 根据用户id查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> selectByUserId(String userId);
}