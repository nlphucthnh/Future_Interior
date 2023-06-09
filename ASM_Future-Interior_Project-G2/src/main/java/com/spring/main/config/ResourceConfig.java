package com.spring.main.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ResourceConfig {
	 @Bean("messageSource")
	    public MessageSource getMessageSource() {
	        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
	        ms.setBasenames("classpath:Manager-login-page"); 
	        ms.setDefaultEncoding("utf-8");
	        return ms;
	    }
}
