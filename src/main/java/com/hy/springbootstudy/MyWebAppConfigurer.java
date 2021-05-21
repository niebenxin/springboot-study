package com.hy.springbootstudy;

import com.hy.springbootstudy.util.String2DateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/idea2020work/springboot-study/src/main/resources/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //添加字符串转换Date的自定义转换器
        registry.addConverter(new String2DateConverter());
    }
}
