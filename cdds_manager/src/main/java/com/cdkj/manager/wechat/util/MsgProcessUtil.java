/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.cdkj.manager.wechat.util;


import com.cdkj.manager.wechat.util.msg.in.*;
import com.cdkj.manager.wechat.util.msg.in.card.*;
import com.cdkj.manager.wechat.util.msg.in.event.*;
import com.cdkj.manager.wechat.util.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.cdkj.manager.wechat.util.msg.out.OutTextMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description: 接收微信服务器消息，自动解析成 InMsg 并分发到相应的处理方法 <br>
 * date: 2017/12/21 下午1:44
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public class MsgProcessUtil {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 接收微信消息，进行加工，处理返回逻辑！
     *
     * @param msg 微信推送的消息，可能是会员消息，也可能是事件
     * @return 回复消息
     */
    public String receiveMsg(InMsg msg) {
        //返回“” 防止提示该公众号无法支持服务
        String outMsgXml = "";
        // 解析消息并根据消息类型分发到相应的处理方法
        if (msg instanceof InTextMsg) {
            outMsgXml = processInTextMsg((InTextMsg) msg);
        } else if (msg instanceof InImageMsg) {
            processInImageMsg((InImageMsg) msg);
        } else if (msg instanceof InSpeechRecognitionResults) {
            //update by unas at 2016-1-29, 由于继承InVoiceMsg，需要在InVoiceMsg前判断类型
            processInSpeechRecognitionResults((InSpeechRecognitionResults) msg);
        } else if (msg instanceof InVoiceMsg) {
            processInVoiceMsg((InVoiceMsg) msg);
        } else if (msg instanceof InVideoMsg) {
            processInVideoMsg((InVideoMsg) msg);
        } else if (msg instanceof InShortVideoMsg) {
            //支持小视频
            processInShortVideoMsg((InShortVideoMsg) msg);
        } else if (msg instanceof InLocationMsg) {
            processInLocationMsg((InLocationMsg) msg);
        } else if (msg instanceof InLinkMsg) {
            processInLinkMsg((InLinkMsg) msg);
        } else if (msg instanceof InCustomEvent) {
            processInCustomEvent((InCustomEvent) msg);
        } else if (msg instanceof InFollowEvent) {
            outMsgXml = processInFollowEvent((InFollowEvent) msg);
        } else if (msg instanceof InQrCodeEvent) {
            processInQrCodeEvent((InQrCodeEvent) msg);
        } else if (msg instanceof InLocationEvent) {
            processInLocationEvent((InLocationEvent) msg);
        } else if (msg instanceof InMassEvent) {
            processInMassEvent((InMassEvent) msg);
        } else if (msg instanceof InMenuEvent) {
            processInMenuEvent((InMenuEvent) msg);
        } else if (msg instanceof InTemplateMsgEvent) {
            processInTemplateMsgEvent((InTemplateMsgEvent) msg);
        } else if (msg instanceof InShakearoundUserShakeEvent) {
            processInShakearoundUserShakeEvent((InShakearoundUserShakeEvent) msg);
        } else if (msg instanceof InVerifySuccessEvent) {
            processInVerifySuccessEvent((InVerifySuccessEvent) msg);
        } else if (msg instanceof InVerifyFailEvent) {
            processInVerifyFailEvent((InVerifyFailEvent) msg);
        } else if (msg instanceof InPoiCheckNotifyEvent) {
            processInPoiCheckNotifyEvent((InPoiCheckNotifyEvent) msg);
        } else if (msg instanceof InWifiEvent) {
            processInWifiEvent((InWifiEvent) msg);
        } else if (msg instanceof InUserCardEvent) {
            processInUserCardEvent((InUserCardEvent) msg);
        } else if (msg instanceof InUpdateMemberCardEvent) {
            processInUpdateMemberCardEvent((InUpdateMemberCardEvent) msg);
        } else if (msg instanceof InUserPayFromCardEvent) {
            processInUserPayFromCardEvent((InUserPayFromCardEvent) msg);
        } else if (msg instanceof InMerChantOrderEvent) {
            processInMerChantOrderEvent((InMerChantOrderEvent) msg);
        } else if (msg instanceof InCardPassCheckEvent) {
            processInCardPassCheckEvent((InCardPassCheckEvent) msg);
        } else if (msg instanceof InCardPayOrderEvent) {
            processInCardPayOrderEvent((InCardPayOrderEvent) msg);
        } else if (msg instanceof InCardSkuRemindEvent) {
            processInCardSkuRemindEvent((InCardSkuRemindEvent) msg);
        } else if (msg instanceof InUserConsumeCardEvent) {
            processInUserConsumeCardEvent((InUserConsumeCardEvent) msg);
        } else if (msg instanceof InUserGetCardEvent) {
            processInUserGetCardEvent((InUserGetCardEvent) msg);
        } else if (msg instanceof InUserGiftingCardEvent) {
            processInUserGiftingCardEvent((InUserGiftingCardEvent) msg);
        }//        //===================微信智能硬件========================//
//        else if (msg instanceof InEqubindEvent)
//            processInEqubindEvent((InEqubindEvent) msg);
//        else if (msg instanceof InEquDataMsg)
//            processInEquDataMsg((InEquDataMsg) msg);
//        //===================微信智能硬件========================//
        else if (msg instanceof InNotDefinedEvent) {
            logger.error("未能识别的事件类型。 消息 xml 内容为：\n" + msg);
            processIsNotDefinedEvent((InNotDefinedEvent) msg);
        } else if (msg instanceof InNotDefinedMsg) {
            logger.error("未能识别的消息类型。 消息 xml 内容为：\n" + msg);
            processIsNotDefinedMsg((InNotDefinedMsg) msg);
        }
        return outMsgXml;
    }


    /**
     * 消息输出
     *
     * @param content 输出的消息
     */
    private String renderOutTextMsg(String content, InMsg msg) {
        OutTextMsg outMsg = new OutTextMsg(msg);
        outMsg.setContent(content);
        String outMsgXml = outMsg.toXml();
        logger.debug("\n out msg  :\n{}", outMsgXml);
        return outMsgXml;
    }

    /**
     * 处理接收到的文本消息
     *
     * @param inTextMsg 处理接收到的文本消息
     */
    private String processInTextMsg(InTextMsg inTextMsg) {
        //根据业务逻辑，完成消息处理
        String content = "你好，我是机器人，请不要玩我！";
        return renderOutTextMsg(content, inTextMsg);
    }

    /**
     * 处理接收到的图片消息
     *
     * @param inImageMsg 处理接收到的图片消息
     */
    private void processInImageMsg(InImageMsg inImageMsg) {

    }

    /**
     * 处理接收到的语音消息
     *
     * @param inVoiceMsg 处理接收到的语音消息
     */
    private void processInVoiceMsg(InVoiceMsg inVoiceMsg) {

    }

    /**
     * 处理接收到的视频消息
     *
     * @param inVideoMsg 处理接收到的视频消息
     */
    private void processInVideoMsg(InVideoMsg inVideoMsg) {

    }

    /**
     * 处理接收到的小视频消息
     *
     * @param inShortVideoMsg 处理接收到的小视频消息
     */
    private void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {

    }

    /**
     * 处理接收到的地址位置消息
     *
     * @param inLocationMsg 处理接收到的地址位置消息
     */
    private void processInLocationMsg(InLocationMsg inLocationMsg) {

    }

    /**
     * 处理接收到的链接消息
     *
     * @param inLinkMsg 处理接收到的链接消息
     */
    private void processInLinkMsg(InLinkMsg inLinkMsg) {

    }

    /**
     * 处理接收到的多客服管理事件
     *
     * @param inCustomEvent 处理接收到的多客服管理事件
     */
    private void processInCustomEvent(InCustomEvent inCustomEvent) {

    }

    /**
     * 处理接收到的关注/取消关注事件
     *
     * @param inFollowEvent 处理接收到的关注/取消关注事件
     */
    private String processInFollowEvent(InFollowEvent inFollowEvent) {
        //根据业务逻辑，完成消息处理
        String content = "";
        //如果为关注事件
        if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent())) {
            content = "你好，" + inFollowEvent.getFromUserName() + " 欢迎关注我！";
            inFollowEvent.getToUserName();
            //判断用户在系统中是否存在，如果存在，则从数据库获取相关信息

            //如果用户在数据库不存在，则通过接口拉取相关用户信息
            //获得公众号原始ID信息，根据原始ID获取公众号access_token信息
            String token = "";
            //获取TOKEN信息
//            WeChatUserUtil.takeUserInfo(token, inFollowEvent.getFromUserName());

        }
        // 如果为取消关注事件，将无法接收到传回的信息
        else if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent())) {
            content = "你好，" + inFollowEvent.getFromUserName() + " 取消关注了";

        }
        return renderOutTextMsg(content, inFollowEvent);
    }

    /**
     * 处理接收到的扫描带参数二维码事件
     *
     * @param inQrCodeEvent 处理接收到的扫描带参数二维码事件
     */
    private void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {

    }

    /**
     * 处理接收到的上报地理位置事件
     *
     * @param inLocationEvent 处理接收到的上报地理位置事件
     */
    private void processInLocationEvent(InLocationEvent inLocationEvent) {

    }

    /**
     * 处理接收到的群发任务结束时通知事件
     *
     * @param inMassEvent 处理接收到的群发任务结束时通知事件
     */
    private void processInMassEvent(InMassEvent inMassEvent) {

    }

    /**
     * 处理接收到的自定义菜单事件
     *
     * @param inMenuEvent 处理接收到的自定义菜单事件
     */
    private void processInMenuEvent(InMenuEvent inMenuEvent) {

    }

    /**
     * 处理接收到的语音识别结果
     *
     * @param inSpeechRecognitionResults 处理接收到的语音识别结果
     */
    private void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {

    }

    /**
     * 处理接收到的模板消息是否送达成功通知事件
     *
     * @param inTemplateMsgEvent 处理接收到的模板消息是否送达成功通知事件
     */
    private void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {

    }

    /**
     * 处理微信摇一摇事件
     *
     * @param inShakearoundUserShakeEvent 处理微信摇一摇事件
     */
    private void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {

    }

    /**
     * 资质认证成功 || 名称认证成功 || 年审通知 || 认证过期失效通知
     *
     * @param inVerifySuccessEvent 资质认证成功 || 名称认证成功 || 年审通知 || 认证过期失效通知
     */
    private void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {

    }

    /**
     * 资质认证失败 || 名称认证失败
     *
     * @param inVerifyFailEvent 资质认证失败 || 名称认证失败
     */
    private void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {

    }

    /**
     * 门店在审核事件消息
     *
     * @param inPoiCheckNotifyEvent 门店在审核事件消息
     */
    private void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {

    }

    /**
     * WIFI连网后下发消息 by unas at 2016-1-29
     *
     * @param inWifiEvent WIFI连网后下发消息
     */
    private void processInWifiEvent(InWifiEvent inWifiEvent) {

    }

    /**
     * 1. 微信会员卡二维码扫描领取接口
     * 2. 微信会员卡激活接口
     * 3. 卡券删除事件推送
     * 4. 从卡券进入公众号会话事件推送
     *
     * @param inUserCardEvent InUserCardEvent
     */
    private void processInUserCardEvent(InUserCardEvent inUserCardEvent) {

    }

    /**
     * 微信会员卡积分变更
     *
     * @param inUpdateMemberCardEvent 微信会员卡积分变更
     */
    private void processInUpdateMemberCardEvent(InUpdateMemberCardEvent inUpdateMemberCardEvent) {

    }

    /**
     * 微信会员卡快速买单
     *
     * @param inUserPayFromCardEvent 微信会员卡快速买单
     */
    private void processInUserPayFromCardEvent(InUserPayFromCardEvent inUserPayFromCardEvent) {

    }

    /**
     * 微信小店订单支付成功接口消息
     *
     * @param inMerChantOrderEvent 微信小店订单支付成功接口消息
     */
    private void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent) {

    }

    //

    /**
     * 没有找到对应的事件消息
     *
     * @param inNotDefinedEvent 没有对应的事件消息
     */
    private void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent) {

    }

    /**
     * 没有找到对应的消息
     *
     * @param inNotDefinedMsg 没有对应消息
     */
    private void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg) {

    }

    /**
     * 卡券转赠事件推送
     *
     * @param msg 卡券转赠事件推送
     */
    private void processInUserGiftingCardEvent(InUserGiftingCardEvent msg) {

    }

    /**
     * 卡券领取事件推送
     *
     * @param msg 卡券领取事件推送
     */
    private void processInUserGetCardEvent(InUserGetCardEvent msg) {

    }

    /**
     * 卡券核销事件推送
     *
     * @param msg 卡券核销事件推送
     */
    private void processInUserConsumeCardEvent(InUserConsumeCardEvent msg) {

    }

    /**
     * 卡券库存报警事件
     *
     * @param msg 卡券库存报警事件
     */
    private void processInCardSkuRemindEvent(InCardSkuRemindEvent msg) {

    }

    /**
     * 券点流水详情事件
     *
     * @param msg 券点流水详情事件
     */
    private void processInCardPayOrderEvent(InCardPayOrderEvent msg) {

    }

    /**
     * 审核事件推送
     *
     * @param msg 审核事件推送
     */
    private void processInCardPassCheckEvent(InCardPassCheckEvent msg) {

    }

//    /**
//     * 处理微信硬件绑定和解绑事件
//     * @param msg 处理微信硬件绑定和解绑事件
//     */
//    protected abstract void processInEqubindEvent(InEqubindEvent msg) ;
//
//    /**
//     * 处理微信硬件发来数据
//     * @param msg 处理微信硬件发来数据
//     */
//    protected abstract void processInEquDataMsg(InEquDataMsg msg);
}













