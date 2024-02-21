package com.hyundai.global.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

@Log4j
@EnableWebMvc
@Component
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApplicationProperties properties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(properties.getFRONT_LOCAL_URL() )
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
