package com.hyundai.domain.login.service.oauth;

import com.hyundai.domain.login.dto.kakao.KakaoTokenResponseDto;
import com.hyundai.domain.login.dto.oauth.OAuthMember;
import com.hyundai.domain.login.dto.oauth.OAuthParams;
import com.hyundai.domain.login.entity.enumtype.OAuthProvider;

/**
 * @author : 변형준
 * @fileName : OAuthClient
 * @since : 2/11/24
 */
public interface OAuthClient {
    OAuthProvider oauthProvider();
    KakaoTokenResponseDto getOauthLoginToken(OAuthParams oauthParams);
    OAuthMember getMemberInfo(String accessToken);
}
