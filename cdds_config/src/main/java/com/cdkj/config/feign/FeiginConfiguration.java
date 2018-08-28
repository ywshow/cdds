/**
 * project name:cdds
 * file name:FeiginConfiguration
 * package name:com.cdkj.feign.config
 * date:2018/7/6 14:30
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.config.feign;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * description: 接口config <br>
 * date: 2018/7/6 14:30
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Configuration
public class FeiginConfiguration {
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }

}