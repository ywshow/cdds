/**
 * project name:platform
 * file name:WeChatMessageUtil
 * package name:com.cdkj.wechat.util
 * date:2017/12/7 上午9:20
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.util;

import com.cdkj.manager.wechat.util.aes.WXBizMsgCrypt;
import com.cdkj.manager.wechat.util.aes.WeChatConstants;
import com.cdkj.manager.wechat.util.aes.XMLParse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * description: 微信公众号推送消息处理 <br>
 * date: 2017/12/7 上午9:20
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public class WeChatMessageUtil {

    protected Logger logger = LoggerFactory.getLogger(WeChatMessageUtil.class);


    public String getMessage(String timestamp, String nonce,
                             String msgSignature, String postData) {
        WXBizMsgCrypt pc;
        try {
            pc = new WXBizMsgCrypt("CDKJ_51D251AD9B7019D2E053F401A8C", "CDKJRZ5HpdijaKzQ6TRj3MIGAzDta5s0H1e4LGK3YaC", "wx6001eeeefe06e0af");
            String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, postData);
            logger.debug("解密后明文: \n" + result2);
            //获得Ticket 信息
            Map<String, String> map = XMLParse.xmlToMap(result2);
            String message = "";
//            根据INFO_TYPE 判断是什么类型事件
            if (map.containsKey(WeChatConstants.WECHAT_INFO_TYPE)) {
                String infoType = map.get("InfoType");
                switch (infoType) {
                    case "component_verify_ticket":
                        message = getComponentVerifyTicket(map);
                        break;
                    case "authorized":
                        getAuthorizedInfo(map);
                        break;
                    case "unauthorized":
                        getUnauthorizedInfo(map);
                        break;
                    default:
                        break;
                }
            }
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 微信公众平台推送过来的票据信息
     *
     * @param map 数据信息
     * @return 票据
     */
    private String getComponentVerifyTicket(Map<String, String> map) {
//        <xml>
//            <AppId><![CDATA[wx6001eeeefe06e0af]]></AppId>
//            <CreateTime>1512627534</CreateTime>
//            <InfoType><![CDATA[component_verify_ticket]]></InfoType>
//            <ComponentVerifyTicket><![CDATA[ticket@@@5HCM8qgXR3V4h6Q5UChqzxi0A_4s3YWLDC1xcrQA_tMAS6vZUnJXOUYPRjBBFM0S3vGkHsOz8U9atOZTq0roeg]]></ComponentVerifyTicket>
//        </xml>
        //需要根据结果处理相关逻辑，判断节点数据
        String ticket = map.get("ComponentVerifyTicket");
        String appId = map.get("AppId");

        logger.debug("ticket :" + ticket);
        if (StringUtils.isEmpty(ticket)) {
            logger.debug("ComponentVerifyTicket get failed");
        } else {
            //TODO 放入redis存储，有效期为10分钟
//            redisService.set(RedisKeys.WECHAT_TICKET + appId, ticket, 600);
//            logger.debug("redis ticket:"+redisService.get(RedisKeys.WECHAT_TICKET + appId));
            //判断TOKEN 是否存在，如果不存在，则调用方法获取
//            WeChatUtil.getWeChatApiComponentToken()
            logger.debug("ComponentVerifyTicket get success");

        }
        return ticket;
    }

    /**
     * 获取授权成功信息
     *
     * @param map 推送数据
     * @return 授权信息
     */
    public String getAuthorizedInfo(Map<String, String> map) {
//        同意授权事件
        //<xml>
        //    <AppId><![CDATA[wx6001eeeefe06e0af]]></AppId>
        //    <CreateTime>1512552185</CreateTime>
        //    <InfoType><![CDATA[authorized]]></InfoType>
        //    <AuthorizerAppid><![CDATA[wxf7d2ddffc117e8de]]></AuthorizerAppid>
        //    <AuthorizationCode><![CDATA[queryauthcode@@@y-s2Y--e_oDQxH_PNnGOGGq_4Kho1fVlJU58YHqp4TqAcZfQjsAbASpu77TpfUrZKumCl5hMFjlBrh31C30e6Q]]></AuthorizationCode>
        //    <AuthorizationCodeExpiredTime><![CDATA[1512555785]]></AuthorizationCodeExpiredTime>
        //    <PreAuthCode><![CDATA[preauthcode@@@cy2CSaTTstMDNRMQK_le2DX9qEUiwEIsqTCpbyrVaf34M49hoE47kCoMM0FogkCN]]></PreAuthCode>
        //</xml>

        String authorizerAppid = map.get("AuthorizerAppid");
        String authorizationCode = map.get("AuthorizationCode");
        String authorizationCodeExpiredTime = map.get("AuthorizationCodeExpiredTime");
        String preAuthCode = map.get("PreAuthCode");

        logger.debug("authorized authorizerAppid :{}", authorizerAppid);
        logger.debug("authorized authorizationCode :{}", authorizationCode);
        logger.debug("authorized authorizationCodeExpiredTime :{}", authorizationCodeExpiredTime);
        logger.debug("authorized preAuthCode :{}", preAuthCode);
        //TODO 根据拿到的授权数据，获取相关的权限等信息

        //TODO 通知管理人员，有公众号关注绑定了


        return "";
    }

    /**
     * 获取授权取消信息
     *
     * @param map 授权取消信息
     * @return
     */
    public String getUnauthorizedInfo(Map<String, String> map) {

//          取消授权事件
//        <xml>
//            <AppId><![CDATA[wx6001eeeefe06e0af]]></AppId>
//            <CreateTime>1512552130</CreateTime>
//            <InfoType><![CDATA[unauthorized]]></InfoType><AuthorizerAppid><![CDATA[wxf7d2ddffc117e8de]]></AuthorizerAppid>
//        </xml>
        String authorizerAppid = map.get("AuthorizerAppid");

        logger.debug("unauthorized authorizerAppid :{}", authorizerAppid);
        return authorizerAppid;
    }

}