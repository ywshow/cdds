/**
 * project name:platform
 * file name:WeChatUtil
 * package name:com.cdkj.wechat.controller
 * date:2017/12/5 下午2:59
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.cdkj.util.HttpKit;
import com.cdkj.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 微信工具类 <br>
 * date: 2017/12/5 下午2:59
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public class WeChatUtil {
    //微信接口文档地址：
//    https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=&lang=zh_CN

    /**
     * 微信公众平台令牌获取接口
     */
    private static final String URL_WECHAT_API_COMPONENT_TOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
    /**
     * 微信公众平台票据获取接口
     */
    private static final String URL_WECHAT_JS_API_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    /**
     * 微信预授权码获取接口
     */
    private static final String URL_WECHAT_PRE_AUTH_CODE = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=%s";
    /**
     * 微信授权链接，用于显示在授权网站上
     */
    private static final String URL_WECHAT_AUTH = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=%s";
    /**
     * 微信API权限查询接口
     * http请求方式: POST（请使用https协议）
     * https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=xxxx
     * POST数据示例:
     * {
     * "component_appid":"appid_value" ,
     * "authorization_code": "auth_code_value"
     * }
     */
    private static final String URL_WECHAT_API_QUERY_AUTH = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=%s";
    /**
     * https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=xxxx
     * POST数据示例:
     * {
     * "component_appid":"appid_value" ,
     * "authorizer_appid": "auth_appid_value"
     * }
     */
    private static final String URL_WECHAT_API_GET_AUTHORIZER_INFO = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=%s";
    /**
     * 获取（刷新）授权公众号或小程序的接口调用凭据（令牌）
     * 该API用于在授权方令牌（authorizer_access_token）失效时，可用刷新令牌（authorizer_refresh_token）获取新的令牌。
     * 请注意，此处token是2小时刷新一次，开发者需要自行进行token的缓存，避免token的获取次数达到每日的限定额度。
     * 缓存方法可以参考：http://mp.weixin.qq.com/wiki/2/88b2bf1265a707c031e51f26ca5e6512.html
     * 当换取authorizer_refresh_token后建议保存。
     */
    private static final String URL_WECHAT_AUTHORIZER_REFRESH_TOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=%s";


    protected static Logger logger = LoggerFactory.getLogger(WeChatUtil.class);

    /**
     * 获取微信公众平台的TOKEN信息 有效期2小时
     *
     * @param componentAppId AppId
     * @param appSecret      APP SECRET
     * @param ticket         微信公众平台推送的票据
     * @return token信息
     */
    public static String getWeChatApiComponentToken(String componentAppId, String appSecret, String ticket) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("component_appid", componentAppId);
        paramMap.put("component_appsecret", appSecret);
        paramMap.put("component_verify_ticket", ticket);
        String json = JsonUtils.toJson(paramMap);
        String result = HttpKit.post(URL_WECHAT_API_COMPONENT_TOKEN, json);
        logger.debug("getWeChatApiComponentToken:" + result);
        Map<String, Object> resultMap = JsonUtils.getMaptoFromJson(result);
        if (!ObjectUtils.isEmpty(resultMap)) {
            return resultMap.containsKey("component_access_token") ? String.valueOf(resultMap.get("component_access_token")) : "";
        }
        return "";
    }

    /**
     * 预授权码获取，用于页面授权时使用
     *
     * @param token          TOKEN
     * @param componentAppId APP_ID
     * @return 预授权码
     */
    public static String getPreAuthCode(String token, String componentAppId) {
        String url = String.format(URL_WECHAT_PRE_AUTH_CODE, token);
//        url += url + "&component_appid=" + appid;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("component_appid", componentAppId);
        String json = JsonUtils.toJson(paramMap);
        String result = HttpKit.post(url, json);
        logger.debug("getPreAuthCode:" + result);
        Map<String, Object> resultMap = JsonUtils.getMaptoFromJson(result);
        if (!ObjectUtils.isEmpty(resultMap)) {
            //String expiresIn = resultMap.containsKey("expires_in") ? "" : String.valueOf(resultMap.get("expires_in"));
            return resultMap.containsKey("pre_auth_code") ? String.valueOf(resultMap.get("pre_auth_code")) : "";
        }
        return "";
    }

    /**
     * 获取微信授权链接
     *
     * @param componentAppId APPID
     * @param preAuthCode    授权码
     * @param authType       授权类型 要授权的帐号类型，1则商户扫码后，手机端仅展示公众号、2表示仅展示小程序，3表示公众号和小程序都展示。如果为未制定，则默认小程序和公众号都展示。第三方平台开发者可以使用本字段来控制授权的帐号类型。
     * @return 授权链接
     */
    public static String getUrlWeChatAuth(String componentAppId, String preAuthCode, String redirectUri, String authType) {
        return String.format(URL_WECHAT_AUTH, componentAppId, preAuthCode, redirectUri, authType);
    }

    /**
     * 获取公众号授权信息
     *
     * @param componentAppId    APPID
     * @param authorizationCode 公众号授权时的授权码
     * @return 授权信息
     */
    public static String getUrlWechatApiQueryAuth(String componentAppId, String token, String authorizationCode) {
        String url = String.format(URL_WECHAT_API_QUERY_AUTH, token);
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put("component_appid", componentAppId);
        paramMap.put("authorization_code", authorizationCode);
        String json = JsonUtils.toJson(paramMap);
        String result = HttpKit.post(url, json);
        logger.debug("getUrlWechatApiQueryAuth:" + result);
        Map<String, Object> resultMap = JsonUtils.getMaptoFromJson(result);
        if (!ObjectUtils.isEmpty(resultMap)) {
            return resultMap.containsKey("authorization_info") ? JsonUtils.toJson(resultMap.get("authorization_info")) : "";
        }
        return "";
    }

    /**
     * 获取授权的公众号信息
     *
     * @param token           平台token
     * @param componentAppId  平台appId
     * @param authorizerAppId 授权公众号appId
     * @return 公众号信息
     */
    public static String getAuthorizerInfo(String token, String componentAppId, String authorizerAppId) {
        String url = String.format(URL_WECHAT_API_GET_AUTHORIZER_INFO, token);
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put("component_appid", componentAppId);
        paramMap.put("authorizer_appid", authorizerAppId);
        String json = JsonUtils.toJson(paramMap);
        String result = HttpKit.post(url, json);
        logger.debug("getAuthorizerInfo:" + result);
        Map<String, Object> resultMap = JsonUtils.getMaptoFromJson(result);
        if (!ObjectUtils.isEmpty(resultMap)) {
            return resultMap.containsKey("authorizer_info") ? JsonUtils.toJson(resultMap.get("authorizer_info")) : "";
        }
        return "";
    }

    /**
     * 获取JSAPI 的票据
     * {"errcode":0,"errmsg":"ok","ticket":"kgt8ON7yVITDhtdwci0qeRxJesMxsuhR-5qndtPRFrDJedkVlxBUzLs_77j_SH5azqHQV5YnriaN0AP5fU0iCA","expires_in":7200}
     *
     * @param componentToken token信息
     * @return 票据信息
     */
    public String getJsApiTicket(String componentToken) {
        String url = String.format(URL_WECHAT_JS_API_TICKET_URL, componentToken);
        JSONObject jsonObject;
        String ticket = "";
        jsonObject = JSONObject.parseObject(HttpKit.get(url));
        if (jsonObject != null) {
            ticket = jsonObject.getString("ticket");
        }
        return ticket;
    }

}