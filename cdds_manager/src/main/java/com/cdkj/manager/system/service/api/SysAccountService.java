/**
 * project name:saas
 * file name:SysAccountService
 * package name:com.cdkj.system.service.api
 * date:2018/3/19 14:17
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.service.api;


import com.cdkj.common.base.service.api.BaseService;
import com.cdkj.model.system.pojo.SysAccount;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;

/**
 * description: 平台系统套账号管理 <br>
 * date: 2018/3/19 14:17
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysAccountService extends BaseService<SysAccount, String> {

    /**
     * description: 根据ID查询 <br>
     *
     * @param id ID
     * @return 返回套账号信息
     */
    SysAccount selectById(String id);

    /**
     * description: 根据套账号查询 <br>
     *
     * @param sysAccount 套账号
     * @return 返回套账号信息
     */
    SysAccount selectBySysAccount(String sysAccount);

    /**
     * description: 新增套账号 <br>
     *
     * @param sysAccount 套账号信息
     * @return 记录数
     */
    int insertSysAccount(SysAccount sysAccount);

    /**
     * description: 修改套账号 <br>
     *
     * @param sysAccount 套账号信息
     * @return 记录数
     */
    int updateSysAccount(SysAccount sysAccount);

    /**
     * description: 逻辑删除 <br>
     *
     * @param ids 数组主键
     * @return int
     */
    int deleteLogic(String ids);

    /**
     * 获取所有帐套信息
     *
     * @param pageDTO 参数
     * @return
     */
    ResultInfo<SysAccount> getAll(PageDTO pageDTO);
}