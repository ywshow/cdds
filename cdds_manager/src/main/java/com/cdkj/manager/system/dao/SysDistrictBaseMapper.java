package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysDistrictBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDistrictBaseMapper extends BaseMapper<SysDistrictBase,String> {
    /**
     * 通过账套号获得账套地址数据基础信息
     * @param sysAccount 账套号
     * @return 账套地址数据信息
     */
    List<SysDistrictBase> selectBySysAccount(@Param("sysAccount") String sysAccount);
}