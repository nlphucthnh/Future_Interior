package com.spring.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class interceptorCofig implements WebMvcConfigurer {
    @Autowired
    MainInterceptor mainWeb;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mainWeb)
                .addPathPatterns("/Manager/**")
                .excludePathPatterns("/assets/**","/**");
    }
}
