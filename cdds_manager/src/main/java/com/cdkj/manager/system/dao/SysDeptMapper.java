package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysDeptMapper extends BaseMapper<SysDept, String> {
    /**
     * 通过userId查询组织机构
     *
     * @param userId
     * @return
     */
    List<SysDept> selectByUserId(@Param("userId") String userId);

    /**
     * 通过parentId查询组织机构
     *
     * @param parentId
     * @return
     */
    List<SysDept> selectByParentId(@Param("parentId") String parentId);

}