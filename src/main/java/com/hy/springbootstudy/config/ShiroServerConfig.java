package com.hy.springbootstudy.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.hy.springbootstudy.service.UserService;
import com.hy.springbootstudy.shiro.RealmDemo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroServerConfig {

    @Autowired
    private UserService userService;

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 密码加密匹配器
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");//加密方式
        credentialsMatcher.setHashIterations(100);//加密次数
        return credentialsMatcher;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realmDemo());
        return securityManager;
    }

    @Bean
    public RealmDemo realmDemo() {
        RealmDemo realmDemo = new RealmDemo();
        realmDemo.setUserService(userService);
        realmDemo.setCredentialsMatcher(credentialsMatcher());
        return realmDemo;
    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        //shiro对象
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login.html");
        bean.setSuccessUrl("/page.html");
        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
        //MAP
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("/images/**", "anon");
        linkedHashMap.put("/upload/**", "anon");
        /*linkedHashMap.put("/login", "anon");
        linkedHashMap.put("/add", "authc");
        linkedHashMap.put("/update", "authc");
        linkedHashMap.put("/page", "authc");
        linkedHashMap.put("/student/**", "anon");*/
        /*linkedHashMap.put("/js/**", "anoo");
        linkedHashMap.put("/css/**", "anoo");
       */
        //未授权界面;
        /*bean.setUnauthorizedUrl("/error");*/
        bean.setFilterChainDefinitionMap(linkedHashMap);
        return bean;
    }

}
