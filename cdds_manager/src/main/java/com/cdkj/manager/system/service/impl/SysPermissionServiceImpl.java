/**
 * project name:saas
 * file name:SysPermissionServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018/2/9 下午1:36
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.manager.system.dao.SysPermissionMapper;
import com.cdkj.manager.system.service.api.SysPermissionService;
import com.cdkj.model.system.pojo.SysPermission;
import com.cdkj.util.BuildTree;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.util.Tree;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * description: 权限管理服务实现 <br>
 * date: 2018/2/9 下午1:36
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, String> implements SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    private List<Tree<SysPermission>> getTrees(List<SysPermission> permissions) {
        List<Tree<SysPermission>> trees = new ArrayList<>();
        for (SysPermission permission : permissions) {
            Tree<SysPermission> tree = new Tree<>();
            tree.setId(permission.getId());
            tree.setParentId(permission.getParentId());
            tree.setText(permission.getPerName());
            Map<String, Object> attributes = new HashMap<>(2);
            attributes.put("url", permission.getPerUrl());
            attributes.put("icon", permission.getPerIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        return trees;
    }

    @Override
    public Set<String> listPermission(String userId) {
        List<String> perms;
        if ("show".equals(ShiroUtils.getUser().getUsername())) {
            perms = sysPermissionMapper.listAllPerms();
        } else {
            perms = sysPermissionMapper.listUserPerms(userId);
        }
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public Tree<SysPermission> permissionTree() {
        List<SysPermission> permissions = sysPermissionMapper.listAllPermission();
        List<Tree<SysPermission>> trees = getTrees(permissions);
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.build(trees);
    }

    @Override
    public Tree<SysPermission> rolePermissionTree(String roleId) {
        List<SysPermission> allPermission = sysPermissionMapper.listAllPermission();
        List<String> rolePermission = sysPermissionMapper.listRoleIdPermission(roleId);
//        for (SysPermission sysPermission : allPermission) {
//            if (rolePermission.contains(sysPermission.getParentId())) {
//                rolePermission.remove(sysPermission.getParentId());
//            }
//        }
        List<Tree<SysPermission>> trees = new ArrayList<>();
        // 默认顶级菜单为０，根据数据库实际情况调整
        // 根据roleId查询权限
        for (SysPermission permission : allPermission) {
            Tree<SysPermission> tree = new Tree<>();
            tree.setId(permission.getId());
            tree.setParentId(permission.getParentId());
            tree.setText(permission.getPerName());
            Map<String, Object> attributes = new HashMap<>(2);
            attributes.put("url", permission.getPerUrl());
            attributes.put("icon", permission.getPerIcon());
            tree.setAttributes(attributes);
            Map<String, Object> state = new HashMap<>(16);
            String permissionId = permission.getId();
            if (rolePermission.contains(permissionId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        return BuildTree.build(trees);
    }


    @Override
    public List<Tree<SysPermission>> listPermissionTree(String userId) {
        List<SysPermission> permissions;
        if ("show".equals(ShiroUtils.getUser().getUsername())) {
            permissions = sysPermissionMapper.listAllPermission();
        } else {
            permissions = sysPermissionMapper.listUserPermission(userId);
        }
        List<Tree<SysPermission>> trees = getTrees(permissions);
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.buildList(trees, "0");
    }

}