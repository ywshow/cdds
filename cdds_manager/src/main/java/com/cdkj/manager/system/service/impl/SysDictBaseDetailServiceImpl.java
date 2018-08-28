/**
 * project name:saas
 * file name:SysDictBaseDetailServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018-03-30 16:20
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.constant.Constant;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.system.service.api.SysDictBaseService;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.model.system.pojo.SysDictBase;
import com.cdkj.util.*;
import com.cdkj.manager.system.dao.SysDictBaseDetailMapper;
import com.cdkj.manager.system.service.api.SysDictBaseDetailService;
import com.cdkj.model.system.pojo.SysDictBaseDetail;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description: //数据字典主表详细信息 <br>
 * date: 2018-03-30 16:20
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysDictBaseDetailServiceImpl extends BaseServiceImpl<SysDictBaseDetail, String> implements SysDictBaseDetailService {

    @Resource
    private SysDictBaseDetailMapper sysDictBaseDetailMapper;

    @Resource
    private SysDictBaseService sysDictBaseService;

    /**
     * 通过组代码获得数据字典主表详细信息
     *
     * @param groupCode 组代码
     * @return 数据字典主表详细信息
     */
    @Override
    public List<SysDictBaseDetail> selectByGroupCode(String groupCode) {
        return sysDictBaseDetailMapper.selectByGroupCode(groupCode);
    }

    /**
     * 通过组代码新增、修改、删除数据字典主表详细信息
     *
     * @param sysDictBaseDetail
     * @return 修改条数
     */
    @Override
    public int insertOrUpdateSysDictBaseDetail(SysDictBaseDetail sysDictBaseDetail) {
        if (StringUtils.isEmpty(sysDictBaseDetail.getId())) {
            this.initDefaultPrpperty(ShiroUtils.getUserId(),sysDictBaseDetail);
            return sysDictBaseDetailMapper.insertSelective(sysDictBaseDetail);
        } else {
            sysDictBaseDetail.setUpdateBy(ShiroUtils.getUserId());
            sysDictBaseDetail.setUpdateDt(DateUtil.getNow());
            return sysDictBaseDetailMapper.updateByPrimaryKeySelective(sysDictBaseDetail);
        }
    }

    /**
     * description: 通过基础字典，查询详情 <br>
     *
     * @param pageDTO sysDictBase主键
     * @return java.util.List<com.cdkj.model.system.pojo.SysDictBaseDetail>
     */
    @Override
    public ResultInfo<SysDictBaseDetail> selectBySysDictBaseId(PageDTO pageDTO) {
        //参数，查询时做校验，map可为空
        Map<String, Object> map = JsonUtils.getMaptoFromJson(pageDTO.getParams().toString());
        String baseId = "baseId";
        if (map.containsKey(baseId) && StringUtil.isNotEmpty(String.valueOf(map.get(baseId)))) {
            SysDictBase sysDictBase = sysDictBaseService.selectByPrimaryKey(String.valueOf(map.get(baseId)));
            if (!ObjectUtils.isEmpty(sysDictBase)) {
                Page page = PageHelper.startPage(pageDTO.getPageNumber(), pageDTO.getPageSize());
                List<SysDictBaseDetail> list = sysDictBaseDetailMapper.selectByGroupCode(sysDictBase.getGroupCode());
                return new ResultInfo(page, list);
            }
        }
        return null;
    }

    /**
     * description: 更改值 <br>
     *
     * @param id         主键
     * @param paramValue 值
     * @return int
     */
    @Override
    public Integer updateParamValueById(String id, String paramValue) {
        if (StringUtil.isEmpty(id)) {
            throw new CustException("ID为空");
        }
        if (StringUtil.isEmpty(paramValue)) {
            throw new CustException("值为空");
        }
        SysDictBaseDetail detail = sysDictBaseDetailMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isEmpty(detail)) {
            detail.setParamValue(paramValue);
            return sysDictBaseDetailMapper.updateByPrimaryKeySelective(detail);
        }
        return null;
    }

    /**
     * description: 根据ID删除 <br>
     *
     * @param ids 主键
     * @return java.lang.Integer
     */
    @Override
    public Integer deleteLogic(String ids) {
        if (StringUtil.isEmpty(ids)) {
            throw new CustException("ID为空");
        }
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            sysDictBaseDetailMapper.deleteLogic(id);
        }
        return idArray.length;
    }
}
