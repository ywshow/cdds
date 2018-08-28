package com.cdkj.common.interceptor;

import com.cdkj.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by bovine on 2016/6/24.
 * 路径权限拦截器
 */
public class AccessPathInterceptor extends HandlerInterceptorAdapter {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private List defaultAccessAllowedFrom;

    public List getDefaultAccessAllowedFrom() {
        return defaultAccessAllowedFrom;
    }

    public void setDefaultAccessAllowedFrom(List defaultAccessAllowedFrom) {
        this.defaultAccessAllowedFrom = defaultAccessAllowedFrom;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = StringUtil.getIp(request);
        logger.debug("AccessPathInterceptor.start.ip={}", ip);
       /* if (!checkIp(response, ip)) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/pages/welcome/index.jsp");
            return false;
        }*/
        return true;
    }

    private boolean checkIp(HttpServletResponse response, String ip) throws IOException {
        if (StringUtil.isNotEmpty(ip)) {
            if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
                return false;
            }
            if (ip.startsWith("192.168.1.")) {
                return true;
            }
        }

        return false;
    }
}