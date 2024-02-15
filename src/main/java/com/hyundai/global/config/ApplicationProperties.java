package com.hyundai.global.config;

import lombok.Getter;
import lombok.Setter;

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
    private String AWS_REGION;
    private String AWS_ACCESS_KEY;
    private String AWS_SECRET_KEY;
    private String AWS_BUCKET;
//    iamport
    private String IMP_API_KEY;
    private String IMP_SECRET_KEY;
}