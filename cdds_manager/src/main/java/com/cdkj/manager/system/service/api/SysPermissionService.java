/**
 * project name:saas
 * file name:SysPermissionService
 * package name:com.cdkj.system.service.api
 * date:2018/2/9 下午1:35
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysPermission;
import com.cdkj.util.Tree;

import java.util.List;
import java.util.Set;

/**
 * description: 权限管理服务层 <br>
 * date: 2018/2/9 下午1:35
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysPermissionService extends BaseService<SysPermission, String> {
    /**
     * 根据UserId获取权限信息
     *
     * @param userId 用户ID
     * @return 权限清单
     */
    Set<String> listPermission(String userId);

    /**
     * 获取用户的权限树信息
     *
     * @return 权限树
     */
    Tree<SysPermission> permissionTree();


    /**
     * 获取角色的权限树信息
     *
     * @return 权限树
     */
    Tree<SysPermission> rolePermissionTree(String roleId);

    /**
     * 获取用户的权限树信息
     *
     * @param userId 用户ID
     * @return 权限树
     */
    List<Tree<SysPermission>> listPermissionTree(String userId);


}