/**
 * project name:sdp-base
 * file name:Base64Util
 * package name:com.cdkj.common.util
 * date:2016-12-26 23:28
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.util;

/**
 * description: Base64 URL 传输处理 <br>
 * date: 2016-12-26 23:28
 *
 * @author jyune
 * @version 1.0
 * @since JDK 1.8
 */

public class Base64Util {

    /**
     *  将 s 进行 BASE64 编码
     * @param s
     * @return
     */
    public static String encode(byte[] s) {
        if (s == null) {
            return null;
        }
        return (new sun.misc.BASE64Encoder()).encode(s);
    }

    /**
     *  将 s 进行 BASE64 编码,针对url的编码
     * @param s
     * @return
     */
    public static String encodeForUrl(byte[] s){
        if (s == null) {
            return null;
        }
        String standerBase64 = encode(s);
        String encodeForUrl = standerBase64;
        //转成针对url的base64编码
        encodeForUrl = encodeForUrl.replace("=", "");
        encodeForUrl = encodeForUrl.replace("+", "*");
        encodeForUrl = encodeForUrl.replace("/", "-");
        //去除换行
        encodeForUrl = encodeForUrl.replace("\n", "");
        encodeForUrl = encodeForUrl.replace("\r", "");

        //转换*号为 -x-
        //防止有违反协议的字符
        encodeForUrl = encodeSpecialLetter1(encodeForUrl);

        return encodeForUrl;

    }

    /**
     * 转换*号为 -x-，
     为了防止有违反协议的字符，-x 转换为-xx
     * @param str
     * @return
     */
    private static String encodeSpecialLetter1(String str){
        str = str.replace("-x", "-xx");
        str = str.replace("*", "-x-");
        return str;
    }

    /**
     * 转换 -x-号为*，-xx转换为-x
     * @param str
     * @return
     */
    private static String decodeSpecialLetter1(String str){
        str = str.replace("-x-", "*");
        str = str.replace("-xx", "-x");
        return str;
    }
    /**
     *  将 s 进行 BASE64 编码
     * @param s
     * @return
     */
    public static String encode(String s) {

        if (s == null) {
            return null;
        }
        return encode(s.getBytes());
    }

    /**将 BASE64 编码的字符串 s 进行解码
     *
     * @param s
     * @return
     */
    public static byte[] decode(String s) {
        if (s == null) {
            return null;
        }
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return b;
        } catch (Exception e) {
            return null;
        }
    }
    /**将 BASE64 编码的字符串 s 进行解码
     *
     * @param s
     * @return
     */
    public static byte[] decodeForUrl(String s) {
        if (s == null) {
            return null;
        }
        s = decodeSpecialLetter1(s);
        s = s.replace("*", "+");
        s = s.replace("-", "/");
        s += "=";
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s );
            return b;
        } catch (Exception e) {
            return null;
        }
    }

}