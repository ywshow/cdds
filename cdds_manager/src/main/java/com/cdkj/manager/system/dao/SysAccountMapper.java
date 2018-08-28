package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * description: 平台系统套账号管理 <br>
 * date: 2018/3/19 14:17
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Component
public interface SysAccountMapper extends BaseMapper<SysAccount, String> {
    /**
     * description: 根据ID查询 <br>
     *
     * @param id ID
     * @return 返回套账号信息
     */
    SysAccount selectById(@Param(value = "id") String id);

    /**
     * description: 根据套账号查询 <br>
     *
     * @param sysAccount 套账号
     * @return 返回套账号信息
     */
    SysAccount selectBySysAccount(@Param(value = "sysAccount") String sysAccount);

    /**
     * description: 逻辑删除 <br>
     *
     * @param id 主键
     * @return int
     */
    int deleteLogic(@Param(value = "id") String id);

    /**
     * description: 所有帐套信息 <br>
     *
     * @param params 查询参数
     * @return 返回套账号信息
     */
    List<SysAccount> getAll(@Param(value = "params") Map<String, Object> params);

}