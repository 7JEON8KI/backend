package com.hyundai.domain.login.dto.oauth;

import com.hyundai.domain.login.entity.enumtype.OAuthProvider;

/**
 * @author : 변형준
 * @fileName : OAuthMember
 * @since : 2/11/24
 */
public interface OAuthMember {
    public String getEmail();
    OAuthProvider getOAuthProvider();
}

