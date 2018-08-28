package com.cdkj.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class ShiroMd5Utils {
    private static final String SALT = "1qazxsw2";

    private static final String ALGORITH_NAME = "md5";

    private static final int HASH_ITERATIONS = 2;

    public static String encrypt(String pswd) {
        return new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
    }

    public static String encrypt(String username, String pswd) {
        return new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT),
                HASH_ITERATIONS).toHex();
    }

    public static String encrypt(String username, String pswd, String salt) {
        return new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + salt),
                HASH_ITERATIONS).toHex();
    }

    public static void main(String[] args) {

        System.out.println(ShiroMd5Utils.encrypt("yw", "123456", "C02CF37B3055418B89010F70482C7351"));
    }

}
