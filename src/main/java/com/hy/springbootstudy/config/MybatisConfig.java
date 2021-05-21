package com.hy.springbootstudy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /*@Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return customizer -> {
            customizer.addInterceptor(pageInterceptor());
        };
    }*/

}
