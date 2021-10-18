package com.simbirsoftintensiv.intensiv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //fixme del уже не надо
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/news").setViewName("news");
//        registry.addViewController("/username").setViewName("username");
//        registry.addViewController("/password").setViewName("password");
    }
}
