/**
 * project name:saas
 * file name:WeChatUrlController
 * package name:com.cdkj.wechat.controller
 * date:2018/3/29 下午5:28
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.StringUtil;
import com.cdkj.constant.SysDictConstants;
import com.cdkj.manager.wechat.service.api.WeChatService;
import com.cdkj.manager.wechat.service.api.WechatFuncInfoService;
import com.cdkj.manager.wechat.util.ApiResult;
import com.cdkj.manager.wechat.util.WeChatUserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * description: 授权控制器 <br>
 * date: 2018/3/29 下午5:28
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/wechat/url/")
@PropertySource("classpath:config/wechat.properties")
public class WeChatUrlController extends BaseController {

    @Resource
    private WeChatService weChatService;

    @Value("${wechat.appid}")
    private String componentAppId;
    @Resource
    private WechatFuncInfoService wechatFuncInfoService;

    /**
     * 不通过前端，后台直接获取微信CODE
     *
     * @param sysAccount  帐套号
     * @param redirectUri 回调路径
     * @param state       为空或者指定的值
     * @param scope       true snsapi_base ；false snsapi_userinfo
     * @return
     */
    @RequestMapping("/open/getWechatCode/{sysAccount}/{scope}")
    public String getWechatCode(HttpServletRequest request, @PathVariable("sysAccount") String sysAccount,
                                @RequestParam(value = "redirectUri", required = false) String redirectUri,
                                @RequestParam(value = "state", required = false) String state, @PathVariable("scope") String scope) {
        logger.debug("WechatUrlController.getWechatCode.sysAccount={},redirectUri={},state={},scope={}", sysAccount, redirectUri, state, scope);
        if (!StringUtil.areNotEmpty(sysAccount)) {
            return JsonUtils.resFailed("参数异常");
        }
        logger.debug("重定向地址1：" + redirectUri);

        //根据帐套获取APPID
        String appId = "wxf7d2ddffc117e8de";
//        appId = redisClient.get(RedisKeys.WECHAT_AUTHORIZER_APPID + sysAccount);

//        String scope;
        //通过REDIS字典服务，提供数据查询

        //需要先存前端给的地址redirectUri，然后换成本接口地址。
        String code = request.getParameter("code");
        //根据帐套获取微信配置信息
        if (ObjectUtils.isEmpty(code)) {
            request.getSession().setAttribute("WECHAT_CODE_REDIRECT_URI", redirectUri);
            String url = "wechat.hubeta.com/wechat/url/open/getWechatCode/" + sysAccount + "/" + scope;
            logger.debug("从微信服务端获取code失败,重试获取！");
            logger.debug("重定向地址2：" + url);
            return "redirect:" + WeChatUserUtil.getWechatCodeUrl(appId, url, scope, state, componentAppId);
        }
        logger.debug("重定向地址3：" + redirectUri);
        redirectUri = (String) request.getSession().getAttribute("WECHAT_CODE_REDIRECT_URI");
        try {
            //如果有code，则用code 去获取openId，通过openId获取会员信息
            //并且跳转到 传入的URL
            ApiResult result = null;
            if ("snsapi_userinfo".equals(scope)) {
                result = WeChatUserUtil.getOpenIdByCodeScopeUserInfo(appId, code, "authorization_code", componentAppId, weChatService.getWeChatApiComponentToken());
            }
            if ("snsapi_base".equals(scope)) {
                result = WeChatUserUtil.getOpenIdByCodeScopeBase(appId, code, "authorization_code", componentAppId, weChatService.getWeChatApiComponentToken());

            }

            //使用openId获取用户信息
            logger.debug("从微信服务端获取信息成功：{}", result);
            return "redirect:" + redirectUri;
        } catch (Exception e) {
            logger.error("WechatUrlController.getWechatCode.error", e);
            return JsonUtils.resFailed("服务器异常，请稍后重试！");
        }
    }

    /**
     * 获取微信权限url
     *
     * @param sysAccount  帐套号
     * @param redirectUri 回调路径
     * @param state       为空或者指定的值
     * @param scope       snsapi_base, snsapi_userinfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/open/getAuthorizeURL")
    public String getAuthorizeURL(String sysAccount, String redirectUri, String scope, String state) {
        logger.debug("WechatUrlController.getAuthorizeURL.sysAccount={},redirectUri={},state={},scope={}", sysAccount, redirectUri, state, scope);
        if (!StringUtil.areNotEmpty(sysAccount, redirectUri)) {
            return JsonUtils.resFailed("参数异常");
        }
        try {
            String appId = redisClient.get(SysDictConstants.PREFIX + "-" + SysDictConstants.GROUP_CODE.WE_CHAT_TYPE + "-" + SysDictConstants.WE_CHAT_TYPE.WX_APP_ID + "-" + sysAccount);
            if (ObjectUtils.isEmpty(appId)) {
                //调用数据admin 中的服务 获得APP ID信息

            }
            appId = "wxf7d2ddffc117e8de";
            //查库里的授权码
            //根据帐套获取对应的信息
            String url = WeChatUserUtil.getWechatCodeUrl(appId, redirectUri, scope, state, componentAppId);
            return JsonUtils.res(url);
        } catch (Exception e) {
            logger.error("WechatUrlController.getAuthorizeURL.error", e);
            return JsonUtils.resFailed("系统异常");
        }
    }

    @ResponseBody
    @RequestMapping("/open/user")
    public String userInfo() {
        return "true";
    }
}