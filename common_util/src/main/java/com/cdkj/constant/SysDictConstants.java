/**
 * project name:saas
 * file name:SysDictConstants
 * package name:com.cdkj.commom.constants
 * date:2018/3/29 下午9:05
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.constant;

/**
 * description: 数据字典通用常量 <br>
 * date: 2018/3/29 下午9:05
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public interface SysDictConstants {

    String PREFIX = "SYS_DICT";

    interface GROUP_CODE {
        String MSG_TYPE = "MsgType";
        String WITHDRAW_TYPE = "WithdrawType";
        String WE_CHAT_TYPE = "WeChatType";
    }


    interface MSG_TYPE {
        /**
         * 短信接口密码
         */
        String SMS_ACCOUNT_PWD = "SMS_ACCOUNT_PWD";
        /**
         * 短信接口账号
         */
        String SMS_ACCOUNT = "SMS_ACCOUNT";
        /**
         * 短信接口地址
         */
        String SMS_INTERFACE_ADDR = "SMS_INTERFACE_ADDR";
        /**
         * 微服务加密DesKey
         */
        String SMS_DESKEY = "SMS_DESKEY";
        /**
         * 短信签名
         */
        String SMS_SIGN = "SMS_SIGN";

    }

    interface WITHDRAW_TYPE {
        /**
         * 提现API KEY
         */
        String WDT_API_KEY = "WDT_API_KEY";
        /**
         * 商户ID
         */
        String WDT_MCH_ID = "WDT_MCH_ID";
        /**
         * 证书文件路径
         */
        String WDT_CERT = "WDT_CERT";
        /**
         * PKCS8密钥文件路径
         */
        String WDT_KEYFILE_PKCS8 = "WDT_KEYFILE_PKCS8";
    }

    interface WE_CHAT_TYPE {
        /**
         * 微信APPID
         */
        String WX_APP_ID = "WX_APP_ID";
    }

    /**
     * 登录模式
     */
    interface LOGINAPP {
        //手机登录
        int LOGIN_MODEL_MOBILE = 0;

        //账户密码登录
        int LOGIN_MODEL_ACCOUNT = 1;

        //app登录
        int SOURCE_LOGIN_APP = 0;

        //web登录
        int SOURCE_LOGIN_WEB = 1;
    }
}