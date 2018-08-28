/**
 * project name:cdds
 * file name:RedisConfig
 * package name:com.cdkj.config.redis
 * date:2018/7/18 0:02
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.config.redis;

import com.cdkj.common.redis.JedisClusterFactory;
import com.cdkj.common.redis.RedisClient;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * description: redis配置 <br>
 * date: 2018/6/22 0:02
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Configuration
@PropertySource("classpath:config/redis.properties")
public class RedisConfig {
    @Value("${redis.maxIdle}")
    private Integer maxIdle;

    @Value("${redis.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.minIdle}")
    private Integer minIdle;

    @Value("${redis.cluster.nodes}")
    private String nodes;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.timeout}")
    private Integer timeout;

    @Value("${redis.maxRedirections}")
    private Integer maxRedirections;


    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setTestOnBorrow(testOnBorrow);
        return genericObjectPoolConfig;
    }

    @Bean
    public JedisClusterFactory jedisCluster() {
        JedisClusterFactory jedisClusterFactory = new JedisClusterFactory();
        ClassPathResource classPathResource = new ClassPathResource("config/redis.properties");
        jedisClusterFactory.setAddressConfig(classPathResource);
        jedisClusterFactory.setHostPrefix(nodes);
        jedisClusterFactory.setPassword(password);
        jedisClusterFactory.setTimeout(timeout);
        jedisClusterFactory.setMaxRedirections(maxRedirections);
        jedisClusterFactory.setGenericObjectPoolConfig(genericObjectPoolConfig());
        return jedisClusterFactory;
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory(new RedisClusterConfiguration());
    }

    @Bean
    public RedisClient redisClient() {
        RedisClient redisClient = new RedisClient();
        redisClient.setJedisClusterFactory(jedisCluster());
        return redisClient;
    }
}