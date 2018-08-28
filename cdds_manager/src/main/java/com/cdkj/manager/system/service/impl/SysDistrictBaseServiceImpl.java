/**
 * project name:saas
 * file name:SysDistrictBaseServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018-03-31 9:45
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.util.DateUtil;
import com.cdkj.manager.system.dao.SysDistrictBaseMapper;
import com.cdkj.manager.system.service.api.SysDistrictBaseService;
import com.cdkj.model.system.pojo.SysDistrictBase;
import com.cdkj.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: //账套地址基础数据 <br>
 * date: 2018-03-31 9:45
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysDistrictBaseServiceImpl extends BaseServiceImpl<SysDistrictBase, String> implements SysDistrictBaseService {

    @Resource
    private SysDistrictBaseMapper sysDistrictBaseMapper;

    /**
     * 根据账套号查询账套地址基础数据表
     *
     * @param sysAccount 账套号
     * @return 账套地址数据
     */
    @Override
    public List<SysDistrictBase> selectBySysAccount(String sysAccount) {
        return sysDistrictBaseMapper.selectBySysAccount(sysAccount);
    }

    /**
     * 新增、修改、删除账套地址基础数据
     *
     * @param sysDistrictBase
     * @return 修改条数
     */
    @Override
    public int insertOrUpdateSysDistrictBase(SysDistrictBase sysDistrictBase) {
        if (StringUtils.isEmpty(sysDistrictBase.getId())) {
            sysDistrictBase.setId(UUIDGenerator.randomUUID());
            sysDistrictBase.setCreateDt(DateUtil.getNow());
            return sysDistrictBaseMapper.insertSelective(sysDistrictBase);
        } else {
            sysDistrictBase.setUpdateDt(DateUtil.getNow());
            return sysDistrictBaseMapper.updateByPrimaryKeySelective(sysDistrictBase);
        }
    }
}
