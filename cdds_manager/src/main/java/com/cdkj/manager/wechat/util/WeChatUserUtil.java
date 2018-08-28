/**
 * project name:platform
 * file name:WeChatUserUtil
 * package name:com.cdkj.wechat.util
 * date:2017/12/12 下午5:17
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.util;

import com.alibaba.fastjson.JSON;
import com.cdkj.util.HttpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * description:  微信用户相关操作工具类 <br>
 * date: 2017/12/12 下午5:17
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public class WeChatUserUtil {
    /**
     * 第一步：请求CODE
     * 请求方法
     * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（一般而言，已微信认证的服务号拥有snsapi_base和snsapi_userinfo），使用微信客户端打开以下链接（严格按照以下格式，包括顺序和大小写，并请将参数替换为实际内容）：
     * doc: https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318590&token=&lang=zh_CN
     */
    private static final String URL_WECHAT_AUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize" +
            "?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&component_appid=%s#wechat_redirect";
    /**
     * 第二步：通过code换取access_token
     * 请求方法
     * 获取第一步的code后，请求以下链接获取access_token：
     */
    private static final String URL_WECHAT_USER_TOKE = "https://api.weixin.qq.com/sns/oauth2/component/access_token" +
            "?appid=%s&code=%s&grant_type=%s&component_appid=%s&component_access_token=%s";
    /**
     * 第三部：使用token 和openId
     * 要求SCOPE =snsapi_userinfo
     * 获取用户信息接口
     */
    private static final String URL_WECHAT_TAKE_USER_INFO_SNS = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    private static final String URL_WECHAT_TAKE_USER_BASE = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    private static final String URL_WECHAT_TAKE_USER_REFRESH_TOKE = "https://api.weixin.qq.com/sns/oauth2/component/refresh_token" +
            "?appid=%s&grant_type=refresh_token&component_appid=%s&component_access_token=%s&refresh_token=%s";
    protected static Logger logger = LoggerFactory.getLogger(WeChatUserUtil.class);
    private static String URL_WECHAT_OPENID_BY_CODE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=%s";

    /**
     * 1.获得前端的授权申请
     */
    public static String getWechatCodeUrl(String appId, String redirectUri, String scope, String state, String componentAppId) {
        return String.format(URL_WECHAT_AUTH_CODE, appId, redirectUri, scope, state, componentAppId);
    }

    /**
     * 2.根据微信的CODE拿到OPENID信息
     *
     * @param code              微信CODE
     * @param appId             微信appId
     * @param authorizationCode 微信公众在第三方平台的授权码
     * @return OpenId
     */
    public static ApiResult getOpenIdByCodeScopeUserInfo(String appId, String code, String authorizationCode, String componentAppId, String componentAccessToken) {
        String url = String.format(URL_WECHAT_USER_TOKE, appId, code, authorizationCode, componentAppId, componentAccessToken);
//        {
//            "access_token":"ACCESS_TOKEN",
//                "expires_in":7200,
//                "refresh_token":"REFRESH_TOKEN",
//                "openid":"OPENID",
//                "scope":"SCOPE"
//        }
        String jsonResult = HttpKit.post(url, "1");
        logger.debug("WeChatUserUtil.getOpenIdByCode.result={}", jsonResult);
        Map map = JSON.parseObject(jsonResult, Map.class);
        if (map.containsKey("openid")) {
            String openId = String.valueOf(map.get("openid"));
            String accessToken = String.valueOf(map.get("access_token"));
//            3.获取用户信息
            String userUrl = String.format(URL_WECHAT_TAKE_USER_INFO_SNS, accessToken, openId);
            String result = HttpKit.get(userUrl);
            logger.debug("WeChatUtil.TakeUserInfo.result={}", result);
            Map<String, Object> userMap = JSON.parseObject(result, Map.class);
            if (map.containsKey("errorcode")) {
                if ("40001".equals(String.valueOf(map.get("errorcode")))) {

                }
            }
            return StringUtils.isEmpty(result) ? null : new ApiResult(result);
        } else {
            logger.error("getOpenIdByCode Fail error message:{}", jsonResult);
        }
        return null;
    }

    /**
     * 2.根据微信的CODE拿到OPENID信息
     *
     * @param code              微信CODE
     * @param appId             微信appId
     * @param authorizationCode 微信公众在第三方平台的授权码
     * @return OpenId
     */
    public static ApiResult getOpenIdByCodeScopeBase(String appId, String code, String authorizationCode, String componentAppId, String componentAccessToken) {
        String url = String.format(URL_WECHAT_USER_TOKE, appId, code, authorizationCode, componentAppId, componentAccessToken);
//        {
//            "access_token":"ACCESS_TOKEN",
//                "expires_in":7200,
//                "refresh_token":"REFRESH_TOKEN",
//                "openid":"OPENID",
//                "scope":"SCOPE"
//        }
        String jsonResult = HttpKit.post(url, "1");
        logger.debug("WeChatUserUtil.getOpenIdByCode.result={}", jsonResult);
        Map map = JSON.parseObject(jsonResult, Map.class);
        if (map.containsKey("openid")) {
            String openId = String.valueOf(map.get("openid"));
            String accessToken = String.valueOf(map.get("access_token"));
//            3.获取用户信息
            String userUrl = String.format(URL_WECHAT_TAKE_USER_BASE, accessToken, openId);
            String result = HttpKit.get(userUrl);
            logger.debug("WeChatUtil.TakeUserInfo.result={}", result);
            return StringUtils.isEmpty(result) ? null : new ApiResult(result);
        } else {
            logger.error("getOpenIdByCode Fail error message:{}", jsonResult);
        }
        return null;
    }
}