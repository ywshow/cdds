/**
 * project name:platform
 * file name:WeChatAuthUtil
 * package name:com.cdkj.wechat.util
 * date:2017/12/12 下午5:32
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.util;

/**
 * description: //TODO <br>
 * date: 2017/12/12 下午5:32
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public class WeChatAuthUtil {
    /**
     * 根据微信的CODE拿到OPENID信息
     *
     * @param code 微信CODE
     * @return OpenId
     */
    public static String getOpenIdByCode(String code, String appId) {
//        String url = PropUtils.getString("wx.path.normal.token");
//
////        url = String.format(url, wechat.getWxAppId(), wechat.getWxSecretKey(), code);
//        String openId = "";
//        for (int i = 0; i < 3; i++) {
//            try {
//                String jsonResult = HttpKit.post(url, "1");
//                logger.debug("getOpenIdByCode.jsonResult={}", jsonResult);
//                Map<String, Object> map = JsonUtils.getMaptoFromJson(jsonResult);
//                if (map.containsKey("openid")) {
//                    openId = String.valueOf(map.get("openid"));
//                    return openId;
//                }
//                if (StringUtil.isNotEmpty(openId)) {
//                    break;
//                } else if (map.containsKey("errcode")) {
//                    logger.error("getOpenIdByCode的第{}次.result = {}", i + 1, jsonResult);
//                    break;
//                } else {
//                    logger.error("getOpenIdByCode的第{}次.openId is null,code={},,result = {}", i + 1, code, jsonResult);
//                }
//            } catch (Exception ex) {
//                logger.error("getOpenIdByCode.error", ex);
//            }
//        }
//        return openId;
        return "";
    }
}