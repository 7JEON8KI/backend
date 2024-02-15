package com.hyundai.global.config;

import lombok.*;

@Getter
@Setter
public class ApplicationProperties {
    private String FRONT_LOCAL_URL;
    private String DOMAIN;
    private String KAKAO_REDIRECT_URI;
    private String KAKAO_CLIENT_ID;
    private String KAKAO_TOKEN_URL;
    private String KAKAO_USER_URL;
    private String JWT_SECRET_KEY;

//    iamport
    private String IMP_API_KEY;
    private String IMP_SECRET_KEY;
}