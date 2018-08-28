/**
 * project name:saas
 * file name:SysDictBaseService
 * package name:com.cdkj.system.service.api
 * date:2018-03-30 15:13
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysDictBase;
import com.cdkj.ztree.TreeNode;

import java.util.List;

/**
 * description: //数据字典基础主表 <br>
 * date: 2018-03-30 15:13
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysDictBaseService extends BaseService<SysDictBase, String> {

    /**
     * 通过组代码获得数据字典主表信息
     *
     * @param groupCode 组代码
     * @return 数据字典信息
     */
    List<SysDictBase> selectByGroupCode(String groupCode);

    /**
     * 通过组代码新增、修改、删除数据字典主表信息
     *
     * @param sysDictBase
     * @return 修改条数
     */
    int insertOrUpdateSysDictBase(SysDictBase sysDictBase);

    /**
     * description: 获取数据字典树 <br>
     *
     * @param sysDictBase
     * @return tree
     */
    List<TreeNode> getDictBaseTree(SysDictBase sysDictBase);

    /**
     * description: 查询不包含在表SysDict中的字典信息 <br>
     *
     * @param sysAccount
     * @return tree
     */
    List<TreeNode> selectBySysAccountWhereNotInSysDict(String sysAccount);
}
