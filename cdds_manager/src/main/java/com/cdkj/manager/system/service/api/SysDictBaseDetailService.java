/**
 * project name:saas
 * file name:SysDictBaseDetailService
 * package name:com.cdkj.system.service.api
 * date:2018-03-30 16:19
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysDictBaseDetail;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;

import java.util.List;

/**
 * description: //数据字典主表详细信息 <br>
 * date: 2018-03-30 16:19
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysDictBaseDetailService extends BaseService<SysDictBaseDetail, String> {
    /**
     * 通过组代码获得数据字典主表详细信息
     *
     * @param groupCode 组代码
     * @return 数据字典主表详细信息
     */
    List<SysDictBaseDetail> selectByGroupCode(String groupCode);

    /**
     * 通过组代码新增、修改、删除数据字典主表详细信息
     *
     * @param sysDictBaseDetail
     * @return 修改条数
     */
    int insertOrUpdateSysDictBaseDetail(SysDictBaseDetail sysDictBaseDetail);

    /**
     * description: 通过基础字典，查询详情 <br>
     *
     * @param pageDTO sysDictBase主键
     * @return java.util.List<com.cdkj.model.system.pojo.SysDictBaseDetail>
     */
    ResultInfo<SysDictBaseDetail> selectBySysDictBaseId(PageDTO pageDTO);

    /**
     * description: 更改值 <br>
     *
     * @param id         主键
     * @param paramValue 值
     * @return int
     */
    Integer updateParamValueById(String id, String paramValue);

    /**
     * description: 根据ID逻辑删除 <br>
     *
     * @param ids 主键
     * @return java.lang.Integer
     */
    Integer deleteLogic(String ids);
}
