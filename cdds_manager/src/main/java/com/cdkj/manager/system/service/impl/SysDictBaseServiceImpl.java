/**
 * project name:saas
 * file name:SysDictBaseServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018-03-30 15:16
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.system.service.api.SysDictBaseDetailService;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.model.system.pojo.SysDept;
import com.cdkj.model.system.pojo.SysDictBaseDetail;
import com.cdkj.util.*;
import com.cdkj.manager.system.dao.SysDictBaseMapper;
import com.cdkj.manager.system.service.api.SysDictBaseService;
import com.cdkj.model.system.pojo.SysDictBase;
import com.cdkj.ztree.TreeNode;
import com.cdkj.ztree.TreeNodeInit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: //数据字典基础表 <br>
 * date: 2018-03-30 15:16
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysDictBaseServiceImpl extends BaseServiceImpl<SysDictBase, String> implements SysDictBaseService {

    @Resource
    private SysDictBaseMapper sysDictBaseMapper;

    @Resource
    private SysDictBaseDetailService sysDictBaseDetailService;

    /**
     * 通过组代码获得数据字典主表信息
     *
     * @param groupCode 组代码
     * @return 数据字典信息
     */
    @Override
    public List<SysDictBase> selectByGroupCode(String groupCode) {
        return sysDictBaseMapper.selectByGroupCode(groupCode);
    }

    /**
     * 通过组代码新增、修改、删除数据字典主表信息
     *
     * @param sysDictBase
     * @return 修改条数
     */
    @Override
    public int insertOrUpdateSysDictBase(SysDictBase sysDictBase) {
        if (StringUtil.isEmpty(sysDictBase.getGroupName())) {
            throw new CustException("代码组名称为空");
        }
        if (StringUtils.isEmpty(sysDictBase.getId())) {
            if (StringUtil.isEmpty(sysDictBase.getGroupCode())) {
                throw new CustException("代码键为空");
            }
            this.initDefaultPrpperty(ShiroUtils.getUserId(), sysDictBase);
            return sysDictBaseMapper.insertSelective(sysDictBase);
        } else {
            sysDictBase.setUpdateBy(ShiroUtils.getUserId());
            sysDictBase.setUpdateDt(DateUtil.getNow());
            return sysDictBaseMapper.updateByPrimaryKeySelective(sysDictBase);
        }
    }

    /**
     * description: 获取数据字典树 <br>
     *
     * @param sysDictBase
     * @return com.cdkj.util.Tree<com.cdkj.model.system.pojo.SysDictBase>
     */
    @Override
    public List<TreeNode> getDictBaseTree(SysDictBase sysDictBase) {
        List<SysDictBase> list = sysDictBaseMapper.listByPrimaryKeySelective(sysDictBase);
        TreeNodeInit init = new TreeNodeInit();
        String[] property = {"groupName"};
        return init.multipleTree(list, null, property, null);
    }

    /**
     * description: 查询不包含在表SysDict中的字典信息 <br>
     *
     * @param sysAccount
     * @return tree
     */
    @Override
    public List<TreeNode> selectBySysAccountWhereNotInSysDict(String sysAccount) {
        if (StringUtil.isEmpty(sysAccount)) {
            throw new CustException("账套号为空");
        }
        List<SysDictBase> list = sysDictBaseMapper.selectBySysAccountWhereNotInSysDict(sysAccount);
        TreeNodeInit init = new TreeNodeInit();
        String[] property = {"groupName"};
        return init.multipleTree(list, null, property, null);
    }
}
