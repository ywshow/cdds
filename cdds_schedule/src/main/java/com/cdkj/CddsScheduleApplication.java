/**
 * project name:cdds
 * file name:CddsScheduleApplication
 * package name:com.cdkj.schedule
 * date:2018/8/27 12:44
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * description: //TODO <br>
 * date: 2018/8/27 12:44
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan("com.cdkj.**.dao")
public class CddsScheduleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CddsScheduleApplication.class, args);
    }
}