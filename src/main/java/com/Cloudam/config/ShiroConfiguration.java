package com.Cloudam.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.Cloudam.sys.realm.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
    private static final String SHIRO_DIALECT = "shiroDialect";
    private static final String SHIRO_FILTER = "shiroFilter";
    private String hashAlgorithmName = "md5";// 加密方式
    private int hashIterations = 2;// 散列次数


    /**
     * 声明凭证匹配器
     */
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        credentialsMatcher.setHashIterations(hashIterations);
        return credentialsMatcher;
    }

    /**
     * 注入自定义的UserRealm
     * @return
     */
    @Bean
    public UserRealm getUserRealm(CredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        //注入凭证匹配器
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }


    /**
     * 创建DefaultWebSecurityManager对象，关联自定义的UserRealm对象
     * @param userRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm){
        //创建DefaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联自定义realm
        defaultWebSecurityManager.setRealm(userRealm);
        //返回DefaultWebSecurityManager对象
        return defaultWebSecurityManager;
    }

    /**
     * 创建ShiroFilterFactoryBean对象，设置安全管理器
     * @param securityManager
     * @return
     */
    @Bean(SHIRO_FILTER)
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        //创建ShiroFilterFactoryBean对象
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        factoryBean.setSecurityManager(securityManager);
        //设置过滤器链
        Map<String,String> filterChainDefinitionsMap = new LinkedHashMap<String,String>();
        //放行路径（匿名访问）
        filterChainDefinitionsMap.put("/resources/**","anon");//静态资源
        filterChainDefinitionsMap.put("/sys/user/login","anon");//登录请求
        filterChainDefinitionsMap.put("/sys/login","anon");//去到登录页面
        filterChainDefinitionsMap.put("/","anon");//去到登录页面
        filterChainDefinitionsMap.put("/login.html","anon");//去到登录页面
        filterChainDefinitionsMap.put("/favicon.ico","anon");//小图标
        //退出
        filterChainDefinitionsMap.put("/logout","logout");
        //拦截请求
        filterChainDefinitionsMap.put("/**","authc");
        //将过滤器链设置到shiroFilterFactoryBean对象中
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionsMap);
        //身份验证失败要去到登录页面
        //如果不设置loginUrl,则默认找login.jsp页面
        factoryBean.setLoginUrl("/sys/login");
        return factoryBean;
    }

    /**
     * 注册shiro的委托过滤器，相当于之前在web.xml里面配置的
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName(SHIRO_FILTER);
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }
    /* 加入注解的使用，不加入这个注解不生效--开始 */
    /**
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /* 加入注解的使用，不加入这个注解不生效--结束 */

    /**
     * 这里是为了能在html页面引用shiro标签，上面两个函数必须添加，不然会报错
     *
     * @return
     */
    @Bean(name = SHIRO_DIALECT)
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}