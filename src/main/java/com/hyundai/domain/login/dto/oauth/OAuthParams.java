package com.hyundai.domain.login.dto.oauth;

import com.hyundai.domain.login.entity.enumtype.OAuthProvider;
import org.springframework.util.MultiValueMap;

/**
 * @author : 변형준
 * @fileName : OAuthParams
 * @since : 2/11/24
 */
public interface OAuthParams {
    public OAuthProvider oAuthProvider();
    public String getAuthorizationCode();
    public MultiValueMap<String, String> makeBody();
}

