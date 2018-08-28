/**
 * project name:saas
 * file name:SysAccountServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018/3/19 14:18
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.util.*;
import com.cdkj.constant.ErrorCode;
import com.cdkj.manager.system.dao.SysAccountMapper;
import com.cdkj.manager.system.service.api.SysAccountService;
import com.cdkj.model.system.pojo.SysAccount;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 平台系统套账号管理 <br>
 * date: 2018/3/19 14:18
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysAccountServiceImpl extends BaseServiceImpl<SysAccount, String> implements SysAccountService {

    @Resource
    private SysAccountMapper sysAccountMapper;

    /**
     * description: 根据ID查询 <br>
     *
     * @param id ID
     * @return 返回套账号信息
     */
    @Override
    public SysAccount selectById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new CustException(ErrorCode.ERROR_20001, "ID为空");
        }
        return sysAccountMapper.selectById(id);
    }

    /**
     * description: 根据套账号查询 <br>
     *
     * @param sysAccount 套账号
     * @return 返回套账号信息
     */
    @Override
    public SysAccount selectBySysAccount(String sysAccount) {
        if (StringUtils.isEmpty(sysAccount)) {
            throw new CustException(ErrorCode.ERROR_20001, "套账号为空");
        }
        return sysAccountMapper.selectBySysAccount(sysAccount);
    }

    /**
     * description: 新增套账号 <br>
     *
     * @param sysAccount 套账号信息
     * @return 记录数
     */
    @Override
    public int insertSysAccount(SysAccount sysAccount) {
        this.initDefaultPrpperty(ShiroUtils.getUserId(), sysAccount);
        return sysAccountMapper.insertSelective(sysAccount);
    }

    /**
     * description: 修改套账号 <br>
     *
     * @param sysAccount 套账号信息
     * @return 记录数
     */
    @Override
    public int updateSysAccount(SysAccount sysAccount) {
        if (StringUtils.isEmpty(sysAccount.getId())) {
            throw new CustException(ErrorCode.ERROR_20001, "ID为空");
        }
        sysAccount.setUpdateBy(ShiroUtils.getUserId());
        sysAccount.setUpdateDt(DateUtil.getNow());
        return sysAccountMapper.updateByPrimaryKeySelective(sysAccount);
    }

    /**
     * description: 逻辑删除 <br>
     *
     * @param ids 数组主键
     * @return int
     */
    @Override
    public int deleteLogic(String ids) {
        if (StringUtil.isEmpty(ids)) {
            throw new CustException("ID为空");
        }
        String[] array = ids.split(",");
        for (String id : array) {
            sysAccountMapper.deleteLogic(id);
        }
        return array.length;
    }

    @Override
    public ResultInfo<SysAccount> getAll(PageDTO pageDTO) {
        //参数，查询时做校验，map可为空

        Map<String, Object> map = JsonUtils.getMaptoFromJson(pageDTO.getParams().toString());
        Page page = PageHelper.startPage(pageDTO.getPageNumber(), pageDTO.getPageSize());
        Instant instantStart = Instant.now();
        List<SysAccount> list = sysAccountMapper.getAll(map);
        Instant instantEnd = Instant.now();
        System.out.println(">>>>>>>>>>>>createOrderNew：时间差" + Duration.between(instantStart, instantEnd).toMillis());
        return new ResultInfo(page, list);
    }
}