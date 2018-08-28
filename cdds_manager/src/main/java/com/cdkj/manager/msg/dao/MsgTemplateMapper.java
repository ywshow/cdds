package com.cdkj.manager.msg.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.msg.pojo.MsgTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface MsgTemplateMapper extends BaseMapper<MsgTemplate, String> {

    /**
     * 根据模板编码获得模板信息
     *
     * @param sysAccount   帐套号
     * @param templateCode 模板代码
     * @return 模板信息
     */
    MsgTemplate selectByTemplateCode(@Param("sysAccount") String sysAccount, @Param("templateCode") String templateCode);
}