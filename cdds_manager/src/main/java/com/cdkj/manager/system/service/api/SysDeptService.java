/**
 * project name:saas
 * file name:SysDeptService
 * package name:com.cdkj.system.service.api
 * date:2018/3/16 10:06
 * author:WenJunChi
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysDept;
import com.cdkj.ztree.TreeNode;

import java.util.List;

/**
 * description: 组织管理服务层 <br>
 * date: 2018/3/16 10:06
 *
 * @author WenJunChi
 * @version 1.0
 * @since JDK 1.8
 */

public interface SysDeptService extends BaseService<SysDept, String> {
    /**
     * 当传入对象无id时候,执行Insert 操作 否则Update操作
     *
     * @param sysDept
     * @return 操作影响的行
     */
    int merge(SysDept sysDept);

    /**
     * @return 获取组织数结构
     */
    List<TreeNode> sysDeptTree(SysDept sysDept);

    /**
     * 通过用户ID查询用户所在的组织机构
     *
     * @param userId
     * @return 用户的组织机构
     */
    List<SysDept> selectByUserId(String userId);

    /**
     * 父节点ID查询子节点
     *
     * @param parentId
     * @return 获取组织数结构
     */
    List<TreeNode> selectChildrenTree(String parentId);

}