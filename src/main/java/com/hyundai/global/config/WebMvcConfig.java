package com.hyundai.global.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Log4j
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationProperties properties;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(properties.getFRONT_LOCAL_URL() )
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
    }

}
