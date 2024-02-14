package com.hyundai.domain.login.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

/**
 * @author : 변형준
 * @fileName : KakaoTokenResponseDto
 * @since : 2/11/24
 */
@Data
public class KakaoTokenResponseDto {
    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token_expires_in")
    private int refreshTokenExpiresIn;

    private String scope;
}

