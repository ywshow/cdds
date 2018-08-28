/**
 * project name:platform
 * file name:WechatFuncServiceImpl
 * package name:com.cdkj.wechat.service.impl
 * date:2017-12-23 15:04
 * author:zhaozheng
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.service.impl;

import com.cdkj.manager.wechat.dao.WechatFuncInfoMapper;
import com.cdkj.manager.wechat.dao.WechatMainInfoMapper;
import com.cdkj.manager.wechat.service.api.WechatFuncInfoService;
import com.cdkj.model.wechat.pojo.WechatFuncInfo;
import com.cdkj.model.wechat.pojo.WechatMainInfo;
import com.cdkj.util.DateUtil;
import com.cdkj.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description: 公众号主体信息 <br>
 * date: 2017-12-23 15:04
 *
 * @author zhaozheng
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class WechatFuncInfoServiceImpl implements WechatFuncInfoService {

    @Resource
    private WechatMainInfoMapper wechatMainInfoMapper;
    @Resource
    private WechatFuncInfoMapper wechatFuncInfoMapper;

    @Override
    public void addOrUpdateSrvFuncInfo(String authorizerAccessToken, String authorizerRefreshToken, String authorizerAppid, String authorizationCode, List<Map<String, Object>> funcInfo, Map<String, Object> authInfoMap) {
        WechatMainInfo srvWechatMainInfo = wechatMainInfoMapper.selectByAuthAppId(authorizerAppid);

        String nickName = authInfoMap.get("nick_name").toString();
        String headUrl = authInfoMap.get("head_img").toString();
        Map<String, Integer> serviceTypeInfo = (Map<String, Integer>) authInfoMap.get("service_type_info");
        //授权方公众号类型
        Integer serviceTypeInfoId = serviceTypeInfo.get("id");
        Map<String, Integer> verifyTypeInfo = (Map<String, Integer>) authInfoMap.get("verify_type_info");
        //授权方认证类型
        Integer verifyTypeInfoId = verifyTypeInfo.get("id");
        String userName = authInfoMap.get("user_name").toString();
        String principalName = authInfoMap.get("principal_name").toString();
        String alias = authInfoMap.get("alias").toString();
        String qrcodeUrl = authInfoMap.get("qrcode_url").toString();
        boolean tag = false;
        if (srvWechatMainInfo == null) {
            tag = true;
            srvWechatMainInfo = new WechatMainInfo();
            srvWechatMainInfo.setId(UUIDGenerator.randomUUID());
            srvWechatMainInfo.setCreateBy("system");
            srvWechatMainInfo.setCreateDt(DateUtil.getNow());
            srvWechatMainInfo.setSysAccount(authorizerAppid);
        }
        //新增授权记录
        srvWechatMainInfo.setAuthorizerAppid(authorizerAppid);
        srvWechatMainInfo.setAuthorizerRefreshToken(authorizerRefreshToken);
//        srvWechatMainInfo.
        srvWechatMainInfo.setVerifyTypeInfo(verifyTypeInfoId);
        srvWechatMainInfo.setServiceTypeInfo(serviceTypeInfoId);
        srvWechatMainInfo.setUserName(userName);
        srvWechatMainInfo.setNickName(nickName);
        srvWechatMainInfo.setAlias(alias);
        srvWechatMainInfo.setHeadImg(headUrl);
        srvWechatMainInfo.setPrincipalName(principalName);
        srvWechatMainInfo.setQrcodeUrl(qrcodeUrl);
        srvWechatMainInfo.setUpdateDt(DateUtil.getNow());

        if (tag) {
            wechatMainInfoMapper.insertSelective(srvWechatMainInfo);
        } else {
            wechatMainInfoMapper.updateByPrimaryKeySelective(srvWechatMainInfo);
        }
        //删除之前的授权列表
        wechatFuncInfoMapper.deleteByMainId(srvWechatMainInfo.getId());
        //新增授权明细
        for (Map<String, Object> funcInfoItem : funcInfo) {
            Map<String, Integer> funcscopeCategory = (Map<String, Integer>) funcInfoItem.get("funcscope_category");
            Integer id = funcscopeCategory.get("id");
            WechatFuncInfo srvWechatFuncInfo = new WechatFuncInfo();
            srvWechatFuncInfo.setId(UUIDGenerator.randomUUID());
            srvWechatFuncInfo.setSysAccount(srvWechatMainInfo.getSysAccount());
            srvWechatFuncInfo.setCreateBy("system");
            srvWechatFuncInfo.setFuncId(id);
            srvWechatFuncInfo.setMainId(srvWechatMainInfo.getId());
            srvWechatFuncInfo.setCreateDt(DateUtil.getNow());
            wechatFuncInfoMapper.insertSelective(srvWechatFuncInfo);
        }

    }
}