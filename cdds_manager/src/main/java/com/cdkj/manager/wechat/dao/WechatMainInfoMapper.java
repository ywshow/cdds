package com.cdkj.manager.wechat.dao;

import com.cdkj.common.base.model.dao.BaseMapper;
import com.cdkj.model.wechat.pojo.WechatMainInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface WechatMainInfoMapper extends BaseMapper<WechatMainInfo, String> {
    /**
     * 根据APPID 获取授权信息
     *
     * @param authorizerAppid 公众号appId
     * @return 授权信息
     */
    WechatMainInfo selectByAuthAppId(@Param("authorizerAppid") String authorizerAppid);

    /**
     * 根据帐套号获取授权信息
     *
     * @param sysAccount 帐套号
     * @return 授权信息
     */
    WechatMainInfo selectBySysAccount(@Param("sysAccount") String sysAccount);
}