/**
 * project name:sms
 * file name:SignKit
 * package name:com.cdkj.common.security
 * date:2016-11-17 10:32
 * author:Administrator
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util.sms;

import com.cdkj.util.DESUtil;
import com.cdkj.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * description: //签名 加密 解密
 * date: 2016-11-17 10:32
 *
 * @author Arvin
 * @version 1.0
 * @since JDK 1.8
 */
public class SignKit {

    //加密类型 -- 登录加密
    public static final int ENCRYPT_TYPE_LOGIN = 1;
    //加密类型 -- 提交加密
    public static final int ENCRYPT_TYPE_SUBMIT = 2;
    //解密类型 -- 登录解密
    public static final int DECRYPT_TYPE_LOGIN = 1;
    //解密类型 -- 提交解密
    public static final int DECRYPT_TYPE_SUBMIT = 2;
    public static final String DEFAULT_CHARSET = "utf-8";
    private static final Logger logger = LoggerFactory.getLogger(SignKit.class);
    public static String DES_KEY = "";

    /**
     * 加密算法
     *
     * @param params 需要加密的参数
     * @param type   加密类型
     * @return 结果
     */
    public static Map<String, Object> encrypt(Map<String, String> params, int type) {
        switch (type) {
            case ENCRYPT_TYPE_LOGIN:
                return loginEncrypt(params);
            case ENCRYPT_TYPE_SUBMIT:
                return submitEncrypt(params);
            default:
                break;
        }
        return null;
    }

    /**
     * 提交短信加密
     *
     * @param params Map<String, String>
     *               userCode=cdkj
     *               password=123
     *               mobile=18570629027
     *               message=XASDA
     *               token=123123XASD
     *               sendType=1
     *               taskType=123
     * @return xxx
     */
    private static Map<String, Object> submitEncrypt(Map<String, String> params) {
        Map<String, Object> rtnMap = null;
        Map treeMap = null;
        if (null != params && (params instanceof TreeMap)) {
            treeMap = params;
        } else if (null != params) {
            treeMap = new TreeMap<>();
            treeMap.putAll(params);
        }

        try {
            String key = "";
            String value = null;
            StringBuilder sb = new StringBuilder();
            for (Iterator it = treeMap.keySet().iterator(); it.hasNext(); ) {
                key = (String) it.next();
                value = (String) treeMap.get(key);
                sb.append(key + "=" + URLEncoder.encode(value, DEFAULT_CHARSET)).append("&");
            }
            /*url传输串*/
            String urlParams = sb.toString();
            String md5Sign = MD5.MD5Encode(sb.toString(), DEFAULT_CHARSET);

            urlParams += "md5Sign" + "=" + md5Sign;
            String desSign = DESUtil.encrypt(urlParams, DES_KEY);

            rtnMap = new HashMap<>();
            rtnMap.put("sign", desSign);
        } catch (Exception e) {
            logger.error("submitEncrypt.error:{}", e);
        }

        return rtnMap;
    }

    /**
     * 登录参数加密
     *
     * @param params Map<String, String>
     *               mac    mac地址
     *               userCode   用户编码
     *               password   用户密码
     * @return 带签名才参数串 mac=00-21-CC-64-C4-98&password=123&userCode=cdkj&sign=aff98fdfd157aa1a74f7331304761c44
     * 用Des 模糊
     */
    private static Map<String, Object> loginEncrypt(Map<String, String> params) {
        Map<String, Object> rtnMap = null;
        Map treeMap = null;
        if (null != params && (params instanceof TreeMap)) {
            treeMap = params;
        } else if (null != params) {
            treeMap = new TreeMap<>();
            treeMap.putAll(params);
        }

        try {
            String key = "";
            String value = null;
            StringBuilder sb = new StringBuilder();
            for (Iterator it = treeMap.keySet().iterator(); it.hasNext(); ) {
                key = (String) it.next();
                value = (String) treeMap.get(key);
                sb.append(key + "=" + URLEncoder.encode(value, DEFAULT_CHARSET)).append("&");
            }
            /*url传输串*/
            String urlParams = sb.toString();
            /*MAC地址作为盐值*/
            sb.append(params.get("mac"));
            String sign = MD5.MD5Encode(sb.toString(), DEFAULT_CHARSET);
            urlParams += "sign" + "=" + sign;
            rtnMap = new HashMap<>();
            rtnMap.put("urlParams", urlParams);

        } catch (Exception e) {
            logger.error("loginEncrypt.error:{}", e);
        }

        return rtnMap;
    }
}
