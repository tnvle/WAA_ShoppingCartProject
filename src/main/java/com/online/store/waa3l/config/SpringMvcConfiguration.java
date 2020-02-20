package com.online.store.waa3l.config;

import com.online.store.waa3l.formatter.ProductFormatter;
import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.MessageSource;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    ProductFormatter productFormatter;

    @Autowired
    HttpServletRequest request;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(productFormatter);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:errorMessages", "classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(90848820);
        return multipartResolver;
    }
}
