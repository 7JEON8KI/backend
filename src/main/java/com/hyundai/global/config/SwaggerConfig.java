package com.hyundai.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * fileName      : SwaggerConfig
 * author        : 변형준
 * since         : 2/19/24
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        // Authorization 헤더 추가
        Parameter authHeader = new ParameterBuilder()
                .name("Authorization") // 헤더 이름
                .description("Authorization") // 설명
                .modelRef(new ModelRef("string")) // 타입
                .parameterType("header") // 파라미터 타입 (헤더)
                .required(false) // 필수 여부
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(Arrays.asList(authHeader)) // 전역 파라미터 설정
                .select()
                // 여기에 API 선택 조건 추가
                .build();
    }
}
