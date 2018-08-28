/**
 * project name:distribution
 * file name:SnsAccessTokenUtil
 * package name:com.cdkj.wechat.util
 * date:2016/8/8 15:50
 * author:haing
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.util;


import com.cdkj.util.PaymentKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * description: //sns获取用户信息 <br>
 * date: 2016/8/8 15:50
 *
 * @author haing
 * @version 1.0
 * @since JDK 1.8
 */
public class SnsAccessTokenUtil {
    public static String AUTHORIZE_URI = "http://open.weixin.qq.com/connect/oauth2/authorize";
    protected static Logger logger = LoggerFactory.getLogger(SnsAccessTokenUtil.class);

    public SnsAccessTokenUtil() {
    }

    public static String getAuthorizeURL(String appId, String redirectUri, boolean snsapiBase) {
        return getAuthorizeURL(appId, redirectUri, null, snsapiBase);
    }

    public static String getAuthorizeURL(String appId, String redirectUri, String state, boolean snsapiBase) {
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("response_type", "code");
        params.put("redirect_uri", redirectUri);
        if (snsapiBase) {
            params.put("scope", "snsapi_base");
        } else {
            params.put("scope", "snsapi_userinfo");
        }

        if (StringUtils.isEmpty(state)) {
            params.put("state", "wx#wechat_redirect");
        } else {
            params.put("state", state.concat("#wechat_redirect"));
        }

        String para = PaymentKit.packageSign(params);
        return AUTHORIZE_URI + "?" + para;
    }

    public static String getQrConnectURL(String appId, String redirectUri) {
        return getQrConnectURL(appId, redirectUri, null);
    }

    public static String getQrConnectURL(String appId, String redirectUri, String state) {
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("response_type", "code");
        params.put("redirect_uri", redirectUri);
        params.put("scope", "snsapi_login");
        if (StringUtils.isEmpty(state)) {
            params.put("state", "wx#wechat_redirect");
        } else {
            params.put("state", state.concat("#wechat_redirect"));
        }

        String para = PaymentKit.packageSign(params);
        return AUTHORIZE_URI + "?" + para;
    }

//    public static SnsAccessToken getSnsAccessToken(String appId, String secret, String code) {
//        final Map queryParas = ParaMap.create("appid", appId).put("secret", secret).put("code", code).getData();
//        return (SnsAccessToken) RetryUtils.retryOnException(3, (Callable) () -> {
//            String json = HttpKit.get(WeChatConstants.AUTH_CODE_URL, queryParas);
//            return new SnsAccessToken(json);
//        });
//    }
}