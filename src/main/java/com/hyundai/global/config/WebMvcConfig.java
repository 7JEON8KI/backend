package com.hyundai.global.config;

import com.hyundai.global.interceptor.TokenInterceptor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Log4j
@EnableWebMvc
@Service
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private TokenInterceptor tokenInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(properties.getFRONT_LOCAL_URL() )
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns();
    }
}
