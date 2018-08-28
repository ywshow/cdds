/**
 * project name:platform
 * file name:WechatFuncService
 * package name:com.cdkj.wechat.service
 * date:2017-12-23 15:03
 * author:zhaozheng
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.wechat.service.api;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * description: 微信权限支持信息 <br>
 * date: 2017-12-23 15:03
 *
 * @author zhaozheng
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public interface WechatFuncInfoService {
    /**
     * 新增或更新公众号授权信息
     *
     * @param authorizerAccessToken  授权TOKEN
     * @param authorizerRefreshToken 用于过期之后刷新ACCESS_TOKEN
     * @param authorizerAppid        授权公众号ID
     * @param authorizationCode      授权码
     * @param funcInfo               授权代码
     * @param authInfoMap            公众号信息
     */
    void addOrUpdateSrvFuncInfo(String authorizerAccessToken, String authorizerRefreshToken, String authorizerAppid, String authorizationCode,
                                List<Map<String, Object>> funcInfo, Map<String, Object> authInfoMap);
}