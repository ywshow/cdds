/**
 * project name:distribution
 * file name:MqMsgActionConstant
 * package name:com.cdkj.common.constant
 * date:2016/8/13 10:41
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.constant;

/**
 * description: MQ 模板消息请求定义 <br>
 * date: 2016/8/13 10:41
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public interface MqMsgActionConstant {
    /**
     * 会员佣金计算请求地址
     */
    String MQ_ACTION_CUST_COMM_BUILD = "/api/basCustComm/build";
    /**
     * 会员付款请求地址
     */
    String MQ_ACTION_ORDER_PAY = "/api/order/pay";

    /**
     * 会员预支付记录请求地址
     */
    String MQ_ACTION_ORDER_PRE_PAY = "/api/wechat/prepay";

    /**
     * 会员佣金确认
     */
    String MQ_ACTION_CUST_COMM_CONFIRM = "/api/basCustComm/confirm";
    /**
     * 会员佣金取消
     */
    String MQ_ACTION_CUST_COMM_CANCEL = "/api/basCustComm/cancel";
    /**
     * 微信消息发送
     */
    String MQ_ACTION_WECHAT_MSG_SEND = "/wechat/message/send";
    /**
     * 库存计算
     */
    String MQ_ACTION_STOCKING = "/api/stock/inner/destockingSql";
    /**
     * 库存计算
     */
    String MQ_ACTION_SUBTRACT_STOCK = "/api/stock/inner/subtractStockSql";
    /**
     * 添加新用户
     */
    String MQ_ACTION_CUSTOMER_ADD = "/api/basCustomer/addNewCustomer";
    /**
     * 取消关注
     */
    String MQ_ACTION_CUSTOMER_CANCEL_CONCERN = "/api/basCustomer/cancelConcern";
    /**
     * 会员退货申请
     */
    String MQ_ACTION_CUST_REQUEST_HANDLE = "/api/basCustRequest/handle";

    /**
     * 新增积分
     */
    String MQ_ACTION_ADD_POINTS = "/api/points/inner/add";

    /**
     * 添加新用户--APP使用
     */
    String MQ_ACTION_CUSTOMER_ADD_APP = "/api/basCustomer/addNewCustomerApp";

    /**
     * 短信推送
     */
    String MQ_ACTION_SMS_SEND = "/api/sms/inner/send";

    /**
     * 客服消息推送
     */
    String MQ_ACTION_WECHAT_MSG_SEND_TEXT = "/wechat/message/sendText";

    /**
     * 分享二维码过期后，退还剩余库存
     */
    String MQ_ACTION_SHARE_QRCODE_RETURN_CUST_STOCK = "/api/shareQrCode/inner/returnCustStock";

    /**
     * 店铺佣金计算
     */
    String MQ_ACTION_SHOP_COMM_BUILD = "/api/shop/compute";
    /**
     * 计算差价
     */
    String MQ_ACTION_SPREAD_BUILD = "/api/newpolicy/computeSpread";
    /**
     * 计算业绩
     */
    String MQ_ACTION_ACHIEVE_BUILD = "/api/newpolicy/computeAchieve";

    /**
     * 店铺佣金订单确认
     */
    String MQ_ACTION_SHOP_COMM_CONFIRM = "/api/shop/confirm";

    /**
     * 店铺佣金 -- 订单取消、退款动作（出纳审核成功）
     */
    String MQ_ACTION_SHOP_COMM_REFUND = "/api/shop/refund";


    /*------------------------------小算盘相关接口----------------------------------*/
    /**
     * 小算盘同步佣金明细
     */
    String MQ_ACTION_ACH_SYNC_ORDER_COMM = "/api/ach/sync/order/comm";

    /**
     * 小算盘同步总佣金
     */
    String MQ_ACTION_ACH_SYNC_ORDER_TOTAL_COMM = "/api/ach/sync/order/totalComm";

    /**
     * 小算盘同步订单
     */
    String MQ_ACTION_ACH_SYNC_ORDER_TOTAL_ORDER = "/api/ach/sync/order/order";

    /**
     * 礼包检测
     */
    String MQ_ACTION_GIFT_CHECK = "/api/gifts/checkReceive";

    /**
     * 众筹支付订单
     */
    String MQ_ACTION_CFD_ORDER_PAY = "/api/cfd/project/cfdPayCallBack";
}