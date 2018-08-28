package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SysDictMapper extends BaseMapper<SysDict, String> {

    /**
     * 通过组+键获得数据字典信息
     *
     * @param sysAccount 帐套号
     * @param groupCode  组代码
     * @param paramKey   键代码
     * @return 数据字典信息
     */
    String selectByGroupCode(@Param("sysAccount") String sysAccount, @Param("groupCode") String groupCode, @Param("paramKey") String paramKey);

    /**
     * 通过组+键修改数据字典信息
     *
     * @param sysAccount 帐套号
     * @param groupCode  组代码
     * @param paramKey   键代码
     * @return 数据字典信息
     */
    SysDict selectBySysAccountAndGroupCodeAndParamKey(@Param("sysAccount") String sysAccount, @Param("groupCode") String groupCode, @Param("paramKey") String paramKey);

    /**
     * id逻辑删除
     *
     * @param id 主键
     * @return 数据字典信息
     */
    int deleteLogic(@Param("id") String id);

    /**
     * description: 列表查询 <br>
     *
     * @param params 查询参数
     * @return java.util.List<com.cdkj.model.system.pojo.SysDict>
     */
    List<SysDict> selectSysDictList(@Param(value = "params") Map<String, Object> params);

    /**
     * description: 从SysDictBaset同步
     *
     * @param sysAccount 账套号
     * @return int
     */
    int batchInsertFromDictBase(@Param(value = "sysAccount") String sysAccount);

}