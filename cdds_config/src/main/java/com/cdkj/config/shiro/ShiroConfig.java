package com.cdkj.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/upload/**", "anon");
        filterChainDefinitionMap.put("/sys/account/**", "anon");
        filterChainDefinitionMap.put("/files/**", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/blog", "anon");
        filterChainDefinitionMap.put("/blog/open/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/**/swagger/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/**/webjars/**", "anon");
        filterChainDefinitionMap.put("/**/open/**", "anon");
        filterChainDefinitionMap.put("/**/login/**", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("/health", "anon");
        filterChainDefinitionMap.put("/info", "anon");
        filterChainDefinitionMap.put("/beans", "anon");
        filterChainDefinitionMap.put("/actuator", "anon");
        filterChainDefinitionMap.put("/metrics", "anon");
        filterChainDefinitionMap.put("/trace", "anon");

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * description:  Shiro生命周期处理器 <br>
     *
     * @param
     * @return org.apache.shiro.spring.LifecycleBeanPostProcessor
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * description: 使用shiro注解为用户授权
     * [在方法上配置注解@RequiresPermissions("xxx:yyy")] <br>
     *
     * @param
     * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * description: AOP式方法级权限检查 <br>
     *
     * @param securityManager
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * cookie对象;会话Cookie模板
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        cookie.setHttpOnly(true);
        cookie.setMaxAge(86400);
        return cookie;
    }

    /**
     * cookie管理对象;记住我功能,rememberMe管理器
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥
        // 默认AES算法 密钥长度(128 256 512 位) //3AvVhmFLUs0KTA3Kprsdag==
//        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

}
