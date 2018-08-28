package com.cdkj.util;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.util.Base64;

/**
 * Base64(MD5(str))
 * Created by liuhaiyin on 2016/6/20.
 */
public class MD5AndBase64Util {
    protected static Logger logger = LoggerFactory.getLogger(MD5AndBase64Util.class);
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_GBK = "GBK";

    /*public final static byte[] md5(String s, String charset) {
        byte[] md = null;
        try {
            byte[] btInput = s.getBytes(charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            md = mdInst.digest();
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
        return md;
    }*/

    public static String base64Encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String base64Encode(byte[] str) {
        return Base64.getEncoder().encodeToString(str);
    }

    public static byte[] base64Decode(String b) {
        return Base64.getDecoder().decode(b);
    }

    /**
     * Base64(MD5(str))
     *
     * @param s
     * @return
     */
    public static String md5AndBase64(String s) {
        return base64Encode(MD5.md5(s));
        //return base64Encode(md5(s,CHARSET_UTF8));
    }

    /**
     * 校验签名
     * url请求过来的，需要将+转换成功空格
     *
     * @param sign
     * @param params
     * @return
     */
    public static boolean checkSign(String sign, String[] params) {
        return ValidateUtils.equals(sign.replaceAll("\\+", " "), sign(params).replaceAll("\\+", " "));
    }

    /**
     * 生成签名
     *
     * @param params
     * @return
     */
    public static String sign(String[] params) {
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < params.length; i++) {
            content.append(params[i]);
        }
        return MD5AndBase64Util.md5AndBase64(content.toString());
    }

    /**
     * 生成签名
     *
     * @param params
     * @return
     */
    public static String sign(String params) {
        return MD5AndBase64Util.md5AndBase64(params);
    }

    /**
     * 使用RSA进行签名验证,仅仅用于数据量少的时候进行处理
     *
     * @param sign   签名
     * @param key    私钥
     * @param params 参数内容
     * @return
     */
    public static String checkSign(String sign, PrivateKey key, String params) {
        //使用私钥对Sign进行解密
        byte[] msign = RSAUtils.decrypt(key, sign.getBytes());
        String msignStr = String.valueOf(msign);
        //使用私钥对明文进行解密
        byte[] mparams = RSAUtils.decrypt(key, params.getBytes());

        //使用SIGN工具进行签名
        String signStr = sign(params);
        //比较签名内容是否合法
        if (!signStr.equals(msignStr)) {
            return null;
        }
        //返回成功结果
        return String.valueOf(mparams);
    }

    /**
     * 使用DES进行对称解密，并且验证签名是否合法
     *
     * @param sign   使用私钥进行过加密的结果
     * @param dkey   DES KEY
     * @param pkey   私钥
     * @param params 使用DES加密过的参数
     * @return
     */
    public static String checkSign(String sign, String dkey, PrivateKey pkey, String params) {
        //使用公钥对Sign进行解密
        try {
            //1.首先使用私钥拿到dkey的明文
            byte[] mdkey = RSAUtils.decrypt(pkey, base64Decode(dkey));
            //2.使用mdkey进行解密
            params = DESUtil.decrypt(params, new String(mdkey));
            //使用SIGN工具进行签名
            JSONObject jsonObject = JSONObject.fromObject(params);
            jsonObject.remove("sign");
            String signStr = sign(params);
            //比较签名内容是否合法
            if (!signStr.equals(sign)) {
                return null;
            }
        } catch (Exception e) {
            logger.error("check sign des error:{}", e);
        }
        return params;
    }

    /**
     * 生成签名
     *
     * @param params
     * @return
     */
    public static String sign2(String params) {
        return base64Encode(MD5.md54s(params).toUpperCase().getBytes());
    }


}
