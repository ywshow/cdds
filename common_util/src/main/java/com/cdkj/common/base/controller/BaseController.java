package com.cdkj.common.base.controller;

import com.cdkj.common.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * PackageName:com.cdkj.base.controller
 * Descript: 控制类基类 <br/>
 * date: 2016/4/7 <br/>
 * User: jyune
 * version 1.0
 */
public class BaseController<T> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RedisClient redisClient;

}
