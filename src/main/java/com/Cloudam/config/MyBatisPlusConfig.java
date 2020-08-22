package com.Cloudam.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration//标记该类是一个配置类
@EnableTransactionManagement//开启事务管理
@MapperScan(basePackages = {"com.Cloudam.sys.dao"})//加载mapper接口所在的包
public class MyBatisPlusConfig {


    /**
     * 注入分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

}