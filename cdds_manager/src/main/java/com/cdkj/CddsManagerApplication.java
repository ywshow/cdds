/**
 * project name:cdds
 * file name:CddsManagerApplication
 * package name:com.cdkj
 * date:2018/7/14 11:38
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * description: 后台管理 <br>
 * date: 2018/7/14 11:38
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.cdkj.**.dao")
public class CddsManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CddsManagerApplication.class, args);
    }
}