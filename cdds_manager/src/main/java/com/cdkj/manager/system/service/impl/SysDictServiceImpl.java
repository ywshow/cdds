/**
 * project name:saas
 * file name:SysDictServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018-03-30 9:48
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.constant.Constant;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.model.system.pojo.SysUser;
import com.cdkj.util.*;
import com.cdkj.manager.system.dao.SysDictMapper;
import com.cdkj.manager.system.service.api.SysDictService;
import com.cdkj.model.system.pojo.SysDict;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description: 账套号对应的数据字典 <br>
 * date: 2018-03-30 9:48
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDict, String> implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    /**
     * 通过组+键获得数据字典信息
     *
     * @param sysAccount 帐套号
     * @param groupCode  组代码
     * @param paramKey   键代码
     * @return 数据字典信息
     */
    @Override
    public SysDict selectBySysAccountAndGroupCodeAndParamKey(String sysAccount, String groupCode, String paramKey) {
        return sysDictMapper.selectBySysAccountAndGroupCodeAndParamKey(sysAccount, groupCode, paramKey);
    }

    /**
     * 通过数据字典对象新增数据字典信息
     *
     * @param sysDict
     * @return 新增条数
     */
    @Override
    public int insertBySysDictObj(SysDict sysDict) {
        this.initDefaultPrpperty(ShiroUtils.getUserId(), sysDict);
        return sysDictMapper.insertSelective(sysDict);
    }

    /**
     * id逻辑删除
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteLogic(String ids) {
        String[] str = ids.split(",");
        for (String id : str) {
            sysDictMapper.deleteLogic(id);
        }
        return str.length;
    }

    /**
     * description: 列表查询 <br>
     *
     * @param pageDTO
     * @return com.cdkj.util.ResultInfo<com.cdkj.model.system.pojo.SysDict>
     */
    @Override
    public ResultInfo<SysDict> selectSysDictList(PageDTO pageDTO) {
        //参数，查询时做校验，map可为空
        Map<String, Object> map = JsonUtils.getMaptoFromJson(pageDTO.getParams().toString());
        String key = "sysAccount";
        map.put(key, ShiroUtils.getUser().getSysAccount());
        Page page = PageHelper.startPage(pageDTO.getPageNumber(), pageDTO.getPageSize());
        List<SysDict> list = sysDictMapper.selectSysDictList(map);
        return new ResultInfo(page, list);
    }

    /**
     * description: 从SysDictBaset同步 <br>
     *
     * @return int
     */
    @Override
    public int batchInsertFromDictBase() {
        SysUser user = ShiroUtils.getUser();
        if (ObjectUtils.isEmpty(user)) {
            throw new CustException("用户信息为空");
        }
        if (Constant.GLOBAL_SYSACCOUNT.equals(user.getSysAccount())) {
            throw new CustException("登录账户不匹配");
        }
        return sysDictMapper.batchInsertFromDictBase(user.getSysAccount());
    }

    /**
     * description: 修改 <br>
     *
     * @param sysDict 账套信息
     * @return int
     */
    @Override
    public int update(SysDict sysDict) {
        if (StringUtil.isEmpty(sysDict.getId())) {
            throw new CustException("ID为空");
        }
        sysDict.setUpdateDt(ShiroUtils.getUserId());
        sysDict.setUpdateDt(DateUtil.getNow());
        return sysDictMapper.updateByPrimaryKeySelective(sysDict);
    }

}
