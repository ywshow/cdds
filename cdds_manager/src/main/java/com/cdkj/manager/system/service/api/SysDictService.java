/**
 * project name:saas
 * file name:SysDictService
 * package name:com.cdkj.system.service.api
 * date:2018-03-30 9:38
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysDict;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;

/**
 * description: //数据字典 <br>
 * date: 2018-03-30 9:38
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysDictService extends BaseService<SysDict, String> {

    /**
     * 通过组+键获得数据字典信息
     *
     * @param sysAccount 帐套号
     * @param groupCode  组代码
     * @param paramKey   键代码
     * @return 数据字典信息
     */
    SysDict selectBySysAccountAndGroupCodeAndParamKey(String sysAccount, String groupCode, String paramKey);

    /**
     * 通过组+键新增数据字典信息
     *
     * @param sysDict
     * @return 新增条数
     */
    int insertBySysDictObj(SysDict sysDict);

    /**
     * description: id逻辑删除 <br>
     *
     * @param ids
     * @return int
     */
    int deleteLogic(String ids);

    /**
     * description: 列表查询 <br>
     *
     * @param pageDTO
     * @return com.cdkj.util.ResultInfo<com.cdkj.model.system.pojo.SysDict>
     */
    ResultInfo<SysDict> selectSysDictList(PageDTO pageDTO);

    /**
     * description: 从SysDictBase获取记录保存 <br>
     *
     * @return int
     */
    int batchInsertFromDictBase();

    /**
     * description: 修改 <br>
     *
     * @param sysDict 账套信息
     * @return int
     */
    int update(SysDict sysDict);
}
