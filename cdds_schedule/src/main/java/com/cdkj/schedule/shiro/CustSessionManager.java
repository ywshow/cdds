/**
 * project name:saas
 * file name:CustSessionManager
 * package name:com.cdkj.system.shiro
 * date:2018/3/27 上午9:48
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * description: 用于处理前后端分离权限内容 <br>
 * date: 2018/3/27 上午9:48
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public class CustSessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "Authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public CustSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }
}