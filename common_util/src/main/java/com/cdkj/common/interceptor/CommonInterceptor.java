/**
 * project name:sdp-base
 * file name:CommonInterceptor
 * package name:com.cdkj.common.interceptor
 * date:2016/9/2 17:31
 * author:haing
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.common.interceptor;

import com.cdkj.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * description: 公共拦截器 记录日志 <br>
 * date: 2016/9/2 17:31
 *
 * @author haing
 * @version 1.0
 * @since JDK 1.8
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (logger.isDebugEnabled()) {
            if (!request.getRequestURL().toString().contains("/resources/")) {
                Enumeration<String> keys = request.getParameterNames();
                StringBuffer requestParams = new StringBuffer();
                while (keys.hasMoreElements()) {
                    String k = keys.nextElement();
                    requestParams.append(k).append("=").append(request.getParameter(k)).append(",");
                }

                logger.debug("************************************************************************************");
                logger.debug("Request URL:{}", request.getRequestURL().toString());
                logger.debug("Request REMOTE:{}", StringUtil.getIp((request)));
                logger.debug("Request PARAMS:{}", requestParams);
                logger.debug("************************************************************************************");
            }
        }

        //设置通过所有跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        return true;
    }
}