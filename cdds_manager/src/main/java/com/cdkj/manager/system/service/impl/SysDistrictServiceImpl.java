/**
 * project name:saas
 * file name:SysDistrictServiceImpl
 * package name:com.cdkj.system.service.impl
 * date:2018-03-30 17:20
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.cdkj.common.base.service.impl.BaseServiceImpl;
import com.cdkj.util.DateUtil;
import com.cdkj.manager.system.dao.SysDistrictMapper;
import com.cdkj.manager.system.service.api.SysDistrictService;
import com.cdkj.model.system.pojo.SysDistrict;
import com.cdkj.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: //账套地址数据 <br>
 * date: 2018-03-30 17:20
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class SysDistrictServiceImpl extends BaseServiceImpl<SysDistrict, String> implements SysDistrictService {

    @Resource
    private SysDistrictMapper sysDistrictMapper;

    /**
     * 根据账套号查询账套地址数据表
     *
     * @param sysAccount 账套号
     * @return 账套地址数据
     */
    @Override
    public List<SysDistrict> selectBySysAccount(String sysAccount) {
        return sysDistrictMapper.selectBySysAccount(sysAccount);
    }

    /**
     * 新增、修改、删除账套地址数据
     *
     * @param sysDistrict
     * @return 修改条数
     */
    @Override
    public int insertOrUpdateSysDistrict(SysDistrict sysDistrict) {
        if (StringUtils.isEmpty(sysDistrict.getId())) {
            sysDistrict.setId(UUIDGenerator.randomUUID());
            sysDistrict.setCreateDt(DateUtil.getNow());
            return sysDistrictMapper.insertSelective(sysDistrict);
        } else {
            sysDistrict.setUpdateDt(DateUtil.getNow());
            return sysDistrictMapper.updateByPrimaryKeySelective(sysDistrict);
        }
    }
}
