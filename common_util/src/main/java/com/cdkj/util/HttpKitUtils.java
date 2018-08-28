/**
 * project name:cdds
 * file name:HttpKitUtils
 * package name:com.cdkj.util
 * date:2018/6/28 10:56
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util;

import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * description: //TODO <br>
 * date: 2018/6/28 10:56
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public class HttpKitUtils {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * description: get请求，只返回对象信息,参数为Object... <br>
     *
     * @param url
     * @param responseType
     * @param uriVariables
     * @return T
     */
    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) {
        return excuteObject(url, responseType, null, uriVariables);
    }

    /**
     * description: get请求，只返回对象信息，参数为Map <br>
     *
     * @param url
     * @param responseType
     * @param uriVariables
     * @return T
     */
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) {
        return excuteObject(url, responseType, uriVariables, null);
    }

    /**
     * description: get请求，无参 <br>
     *
     * @param url
     * @param responseType
     * @return T
     */
    public <T> T getForObject(String url, Class<T> responseType) {
        try {
            if (StringUtil.isEmpty(url)) {
                throw new CustException(ErrorCode.ERROR_20001, "请求地址为空");
            }
            if (null == responseType) {
                throw new CustException(ErrorCode.ERROR_20001, "转换类型为空");
            }
            URI uri = new URI(url);
            String result = restTemplate.getForObject(uri, String.class);
            return JsonUtils.getObjectFromResultJson(result, responseType);
        } catch (RestClientException | URISyntaxException e) {
            throw new CustException(ErrorCode.ERROR_20002, e.getMessage());
        }
    }

    /**
     * description: 执行操作 <br>
     *
     * @param url
     * @param responseType
     * @param map
     * @param uriVariables
     * @return T
     */
    public <T> T excuteObject(String url, Class<T> responseType, Map<String, ?> map, Object... uriVariables) {
        try {
            if (StringUtil.isEmpty(url)) {
                throw new CustException(ErrorCode.ERROR_20001, "请求地址为空");
            }
            if (null == responseType) {
                throw new CustException(ErrorCode.ERROR_20001, "转换类型为空");
            }
            String result;
            if (null == map) {
                result = restTemplate.getForObject(url, String.class, uriVariables);
            } else {
                result = restTemplate.getForObject(url, String.class, map);
            }
            return JsonUtils.getObjectFromResultJson(result, responseType);
        } catch (RestClientException e) {
            throw new CustException(ErrorCode.ERROR_20002, e.getMessage());
        }
    }
}