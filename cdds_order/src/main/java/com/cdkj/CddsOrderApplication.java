/**
 * project name:cdds
 * file name:CddsOrderApplication
 * package name:com.cdkj
 * date:2018/6/27 17:18
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * description: 订单管理 <br>
 * date: 2018/6/27 17:18
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
//ComponentScan扫描指定的接口jar
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.cdkj.**.dao")
public class CddsOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CddsOrderApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}