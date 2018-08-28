/**
 * project name:distribution
 * file name:MqMsgRoutingKeyConstant
 * package name:com.cdkj.common.constant
 * date:2016/8/13 10:41
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.constant;

/**
 * description: MQ KEY定义，需要与Spring-mq.xml 中一一对应<br>
 * date: 2016/8/13 10:41
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public interface MqMsgRoutingKeyConstant {
    /**
     * 会员佣金管理消息队列
     */
    String MQ_ROUTING_KEY_CUST_COMM = "queue_bas_cust_comm";
    /**
     * 用户微信付款消息队列
     */
    String MQ_ROUTING_KEY_WX_PAY = "queue_wx_pay";
    /**
     * 用户申请退款消息队列
     */
    String MQ_ROUTING_KEY_CUST_REQUEST = "queue_bas_cust_request";
    /**
     * 用户微信付款消息队列
     */
    String MQ_ROUTING_KEY_WECHAT_MSG = "queue_wechat_msg";
    /**
     * 用户新增消息队列
     */
    String MQ_ROUTING_KEY_CUSTOMER_ADD = "queue_add_customer";
    /**
     * 库存管理消息队列
     */
    String MQ_ROUTING_KEY_STOCK = "queue_stock";

    /**
     * 积分管理消息队列
     */
    String MQ_ROUTING_KEY_POINTS = "queue_points";

    /**
     * 分销二期 - 短信推送队列
     */
    String MQ_ROUTING_SMS_SEND = "queue_sms";

    /**
     * 短信微服务 - 用户扣费队列
     */
    String MQ_ROUTING_SMS_BALANCE = "queue_sms_balance";

    /**
     * 级差-店铺返佣计算队列
     */
    String MQ_ROUTING_KEY_SHOP_COMM = "queue_shop_comm";

    /**
     * 礼包-检测是否领完
     */
    String MQ_GIFTS_CHECK = "queue_tld_gifts";
    /**
     * 众筹支付订单
     */
    String QUEUE_CFD_PAY = "queue_cfd_pay";
}