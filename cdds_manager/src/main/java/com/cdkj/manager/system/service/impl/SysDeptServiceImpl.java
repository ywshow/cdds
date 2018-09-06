/**
 * project name:saas
 * file name:SysDeptServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018/3/16 10:07
 * author:WenJunChi
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.util.*;
import com.cdkj.manager.system.dao.SysDeptMapper;
import com.cdkj.manager.system.service.api.SysDeptService;
import com.cdkj.model.system.pojo.SysDept;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.ztree.TreeNode;
import com.cdkj.ztree.TreeNodeInit;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 组织管理服务层实现
 * date: 2018/3/16 10:07
 *
 * @author WenJunChi
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDept, String> implements SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public int merge(SysDept sysDept) {
        if (StringUtils.isEmpty(sysDept.getId())) {
            this.initDefaultPrpperty(ShiroUtils.getUserId(), sysDept);
            if (StringUtil.isEmpty(sysDept.getSysAccount())) {
                sysDept.setSysAccount(ShiroUtils.getUser().getSysAccount());
            }
            return sysDeptMapper.insertSelective(sysDept);
        } else {
            sysDept.setUpdateDt(DateUtil.getNow());
            sysDept.setUpdateBy(ShiroUtils.getUser().getUsername());
            return sysDeptMapper.updateByPrimaryKeySelective(sysDept);
        }
    }

    /**
     * 默认顶级菜单为０，根据数据库实际情况调整
     *
     * @return 返回数结构
     */
    @Override
    public List<TreeNode> sysDeptTree(SysDept sysDept) {
        List<SysDept> sysdepts = sysDeptMapper.listByPrimaryKeySelective(sysDept);
        TreeNodeInit init = new TreeNodeInit();
        String[] property = {"deptName"};
        //map只是为设置sysdepts 列表为父节点
        Map<String, Object> map = new HashMap<>(1);
        map.put("000", "000");
        return init.multipleTree(sysdepts, map, property, null);
    }

    @Override
    public List<SysDept> selectByUserId(String userId) {
        return sysDeptMapper.selectByUserId(userId);
    }

    /**
     * 父节点ID查询子节点
     *
     * @param parentId
     * @return 获取组织数结构
     */
    @Override
    public List<TreeNode> selectChildrenTree(String parentId) {
        if (StringUtil.isEmpty(parentId)) {
            throw new CustException("父节点为空");
        }
        List<SysDept> list = sysDeptMapper.selectByParentId(parentId);
        TreeNodeInit init = new TreeNodeInit();
        String[] property = {"deptName"};
        //map只是为设置sysdepts 列表为父节点
        Map<String, Object> map = new HashMap<>(1);
        map.put("000", "000");
        return init.initChildTreeWithNotParent(list, property, parentId);
    }


    private List<Tree<SysDept>> getTrees(List<SysDept> sysDepts) {
        List<Tree<SysDept>> trees = new ArrayList<>();
        for (SysDept sysDept : sysDepts) {
            Tree<SysDept> tree = new Tree<>();
            tree.setId(sysDept.getId());
            tree.setParentId(sysDept.getParentId());
            tree.setText(sysDept.getDeptName());
            Map<String, Object> attributes = new HashMap<>(2);
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        return trees;
    }
}