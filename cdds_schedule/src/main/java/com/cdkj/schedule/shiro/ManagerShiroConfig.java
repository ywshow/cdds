/**
 * project name:cdds
 * file name:ShiroConfig
 * package name:com.cdkj.manager.shiro
 * date:2018/7/13 14:59
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.schedule.shiro;

import com.cdkj.config.shiro.ShiroConfig;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: shiro单点配置 <br>
 * date: 2018/7/13 14:59
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
@Configuration
public class ManagerShiroConfig extends ShiroConfig {

    @Bean
    UserRealm userRealm(EhCacheManager cacheManager) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(cacheManager);
        return userRealm;
    }

    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:config/ehcache.xml");
        return em;
    }

    @Bean
    SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        manager.setCacheManager(getEhCacheManager());
        manager.setSessionManager(sessionManager());
        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }

    @Bean
    public SessionManager sessionManager() {
        CustSessionManager mySessionManager = new CustSessionManager();
        return mySessionManager;
    }
}