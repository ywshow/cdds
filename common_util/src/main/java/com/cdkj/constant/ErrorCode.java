/**
 * project name:sms
 * file name:ErrorCode
 * package name:com.cdkj.common.constant
 * date:2017-01-12 14:31
 * author:cxt
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.constant;

/**
 * description: //TODO 类功能描述 <br>
 * date: 2017-01-12 14:31
 *
 * @author cxt
 * @version 1.0
 * @since JDK 1.8
 */
public interface ErrorCode {

    //controller、service、job异常

    //输入参数异常
    int ERROR_20001 = 20001;
    //系统异常
    int ERROR_20002 = 20002;
    //乐观锁生效,为防止脏数据,变更佣金取消,回滚相关操作!
    int ERROR_20003 = 20003;
    //无效用户!
    int ERROR_20004 = 20004;
    //用户名/密码无效！
    int ERROR_20005 = 20005;
    //签名失败！
    int ERROR_20006 = 20006;
    //会员不存在！
    int ERROR_20007 = 20007;
    //DES解析失败！
    int ERROR_20008 = 20008;
    //Token失效！
    int ERROR_20009 = 20009;
    //用户密码无效！
    int ERROR_20010 = 20010;
    //短信余额不足,请充值！
    int ERROR_20011 = 20011;
    //URLDecoder失败！
    int ERROR_20012 = 20012;
    //黑名单或退订用户！
    int ERROR_20013 = 20013;
    //短时内发送次数过多！
    int ERROR_20014 = 20014;
    //短信扣费失败！
    int ERROR_20015 = 20015;
    //余额不足！
    int ERROR_20016 = 20016;
    //店铺参数设置
    int ERROR_20020 = 20020;
    //店铺参数设置
    int ERROR_20021 = 20021;
    //开店入围名单
    int ERROR_20022 = 20022;
    //开店会员
    int ERROR_20023 = 20023;
    //返利订单明细
    int ERROR_20024 = 20024;
    //手机号码不合法
    int ERROR_20025 = 20025;

    //验证码错误
    int ERROR_20088 = 20088;
    //字典信息不存在
    int ERROR_20089 = 20089;
    //消息模板配置错误
    int ERROR_20090 = 20090;
    //数据字典的value为空
    int ERROR_20091 = 20091;
    //字典信息刷新失败
    int ERROR_20092 = 20092;
}
