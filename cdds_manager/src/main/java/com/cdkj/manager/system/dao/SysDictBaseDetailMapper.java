package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.SysDictBaseDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysDictBaseDetailMapper extends BaseMapper<SysDictBaseDetail, String> {
    /**
     * 通过组代码获得数据字典主表详细信息
     * @param groupCode  组代码
     * @return 数据字典主表详细数据
     */
    List<SysDictBaseDetail> selectByGroupCode(@Param("groupCode") String groupCode);

    /**
     * description: 逻辑删除 <br>
     *
     * @param id 主键
     * @return int
     */
    int deleteLogic(@Param(value = "id") String id);
}