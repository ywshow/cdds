package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysDictBase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysDictBaseMapper extends BaseMapper<SysDictBase, String> {
    /**
     * 通过组代码获得数据字典主表信息
     *
     * @param groupCode 组代码
     * @return 数据字典主表信息
     */
    List<SysDictBase> selectByGroupCode(@Param("groupCode") String groupCode);

    /**
     * description: 查询不包含在表SysDict中的字典信息 <br>
     *
     * @param sysAccount
     * @return list
     */
    List<SysDictBase> selectBySysAccountWhereNotInSysDict(@Param(value = "sysAccount") String sysAccount);

}