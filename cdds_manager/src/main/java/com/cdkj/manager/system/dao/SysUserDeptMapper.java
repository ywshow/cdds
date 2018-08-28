package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysUserDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface SysUserDeptMapper extends BaseMapper<SysUserDept, String> {
    /**
     * 通过用户ID删除所有关联的用户组织关系
     *
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") String userId);

}