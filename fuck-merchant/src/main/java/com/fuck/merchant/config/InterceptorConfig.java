package com.fuck.merchant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    private TokenInterceptor tokenInterceptor;

    public InterceptorConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/login");
        excludePath.add("/register");
        excludePath.add("/sendMsg");
        registry.addInterceptor(tokenInterceptor).excludePathPatterns(excludePath);
    }
}