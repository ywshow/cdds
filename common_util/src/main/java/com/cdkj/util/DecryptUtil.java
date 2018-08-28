/**
 * project name:sdp-base
 * file name:DecryptUtil
 * package name:com.cdkj.common.util
 * date:2016/8/3 10:46
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;

/**
 * description: 用于应用传参的解密 <br>
 * date: 2016/8/3 10:46
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
public class DecryptUtil {
    protected static Logger logger = LoggerFactory.getLogger(DecryptUtil.class);

    /**
     * 使用DES进行对称解密，并且验证签名是否合法
     *
     * @param dkey   DES KEY
     * @param pkey   私钥
     * @param params 使用DES加密过的参数
     * @return 解密之后的参数信息
     */
    public static String checkParams(String dkey, PrivateKey pkey, String params) {
        //使用公钥对Sign进行解密
        try {
            byte[] keyByte = MD5AndBase64Util.base64Decode(dkey);
            byte[] mdkey = RSAUtils.decrypt(pkey, keyByte);
            //2.使用mdkey进行解密
            logger.debug("checkParams :{}", new String(mdkey));
            params = DESUtil.decrypt(params, new String(mdkey));
            //使用SIGN工具进行签名
            JSONObject jsonObject = JSONObject.fromObject(params);
            String sign = jsonObject.getString("sign");
            jsonObject.remove("sign");
            String signStr = MD5AndBase64Util.sign(jsonObject.toString());
            //比较签名内容是否合法
            if (!signStr.equals(sign)) {
                return JsonUtils.resFailed("签名失败！");
            }
        } catch (Exception e) {
            logger.error("check sign des error:{}", e);
        }
        return params;
    }

}