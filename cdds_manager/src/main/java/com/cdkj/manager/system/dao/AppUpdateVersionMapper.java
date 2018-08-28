package com.cdkj.manager.system.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.system.pojo.AppUpdateVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AppUpdateVersionMapper extends BaseMapper<AppUpdateVersion, String> {

    /**
     * description: 根据条件查询，无则查询全部 <br>
     *
     * @param appUpdateVersion 对象信息
     * @return 返回数据列表
     */
    List<AppUpdateVersion> selectByCondition(@Param(value = "appUpdateVersion") AppUpdateVersion appUpdateVersion);

}