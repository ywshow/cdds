/**
 * project name:saas
 * file name:SysDistrictService
 * package name:com.cdkj.system.service.api
 * date:2018-03-30 17:19
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysDistrict;

import java.util.List;

/**
 * description: //账套地址数据 <br>
 * date: 2018-03-30 17:19
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysDistrictService extends BaseService<SysDistrict, String> {
    /**
     * 根据账套号查询账套地址数据表
     * @param sysAccount 账套号
     * @return 账套地址数据
     */
    List<SysDistrict> selectBySysAccount(String sysAccount);

    /**
     *新增、修改、删除账套地址数据
     * @param sysDistrict
     * @return 修改条数
     */
    int insertOrUpdateSysDistrict(SysDistrict sysDistrict);
}
