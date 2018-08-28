package com.cdkj.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

public class DESUtil {
    /**
     * 加密
     *
     * @param content
     * @param password
     * @return
     */
    public static String encrypt(String content, String password) {
        try {
            return MD5AndBase64Util.base64Encode(encrypt(content.getBytes("utf-8"), password));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content
     * @param password
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String password)
            throws Exception {
        return StringUtil.isEmpty(content) ? null : new String(decrypt(
                MD5AndBase64Util.base64Decode(content), password), "utf-8");
    }

    /**
     * 解密
     *
     * @param content
     * @param password
     * @return
     * @throws Exception
     */
    public static String decodeForUrl(String content, String password)
            throws Exception {
        return StringUtil.isEmpty(content) ? null : new String(decrypt(
                Base64Util.decodeForUrl(content), password), "utf-8");
    }

    public static byte[] decrypt(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }
}