/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.fshows.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author chenhx
 * @version WebMvcConf.java, v 0.1 2018-07-23 下午 1:52
 */
@Configuration
public class WebMvcConf implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //  访问的前缀。  文件真实的存储路径
        registry.addResourceHandler("/**")
                //.addResourceLocations("file:D:/OTA/")//绝对路径
        ;
    }
}