/**
 * project name:platform
 * file name:RedisKeys
 * package name:com.cdkj.redis
 * date:2017/12/8 上午11:31
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.constant;

/**
 * description: redis key信息 <br>
 * date: 2017/12/8 上午11:31
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public interface RedisKeys {

    /**
     * 系统前缀
     */
    String REDIS_KEY_PREFIX = "ADMIN-";

    /**
     * 鉴权TOKEN
     */
    String AUTH_TOKEN = "AUTH-TOKEN-";
    /**
     * 登录token
     */
    String LOGIN_APP_TOKEN = "LOGIN-APP-TOKEN-";

    String SYS_DICT = "SYS-DICT-";

    /**
     * token有效期 - 7天 = 7*24*60*60秒
     */
    Integer LOGIN_APP_TOKEN_VALID_TIME = 604800;

    /**
     * 文件服务接口上传地址
     */
    String SYS_DICT_FILE_UPLOAD_URL = REDIS_KEY_PREFIX + "SYS_DICT-FileType-FILE_UPLOAD_URL";

    /**
     * 文件服务接口登录地址
     */
    String SYS_DICT_FILE_LOGIN_URL = REDIS_KEY_PREFIX + "SYS_DICT-FileType-FILE_LOGIN_URL";

    /**
     * 文件服务接口账号
     */
    String SYS_DICT_FILE_USER_NAME = REDIS_KEY_PREFIX + "SYS_DICT-FileType-FILE_USER_NAME";

    /**
     * 文件服务接口密码
     */
    String SYS_DICT_FILE_PWD = REDIS_KEY_PREFIX + "SYS_DICT-FileType-FILE_PWD";

    /**
     * 文件服务接口秘钥
     */
    String SYS_DICT_FILE_KEY = REDIS_KEY_PREFIX + "SYS_DICT-FileType-FILE_KEY";

    String WECHAT_TICKET = "WECHAT_TICKET-";
    String WECHAT_API_COMPONENT_TOKEN = "WECHAT_API_COMPONENT_TOKEN-";
    String WECHAT_PRE_AUTH_CODE = "WECHAT_PRE_AUTH_CODE-";
    String WECHAT_AUTH_ACCESS_TOKEN = "WECHAT_AUTH_ACCESS_TOKEN-";
    String WECHAT_AUTHORIZER_APPID = "WECHAT_AUTHORIZER_APPID-";

}