package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole, String> {
    /**
     * 通过用户id查询角色关系
     * @param
     * @return
     */
    List<SysRole> selectByUserId(@Param("userId") String userId);

}