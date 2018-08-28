/**
 * project name:saas
 * file name:CorsConfiguration
 * package name:com.cdkj.product.config
 * date:2018/3/25 下午2:45
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.config;

import com.cdkj.common.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * description: 公共配置，跨域问题处理，日志方法格式设置 <br>
 * date: 2018/3/25 下午2:45
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Configuration
public class ConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*")
                .allowedOrigins("*").allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor());
    }
}