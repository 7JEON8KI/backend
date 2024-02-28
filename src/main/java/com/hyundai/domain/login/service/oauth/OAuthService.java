package com.hyundai.domain.login.service.oauth;

import com.hyundai.domain.login.dto.MemberInfoRequestDto;
import com.hyundai.domain.login.dto.StoreRequestDto;
import com.hyundai.domain.login.dto.kakao.KakaoLoginResponseDto;
import com.hyundai.domain.login.dto.oauth.OAuthMemberRequestDto;
import com.hyundai.domain.login.dto.oauth.OAuthParams;

/**
 * fileName      : OAuthService
 * author        : 변형준
 * since         : 2/19/24
 */
public interface OAuthService {
    KakaoLoginResponseDto getMemberByOauthLogin(OAuthParams oAuthParams);
    String saveMember(OAuthMemberRequestDto oAuthMemberRequestDto);
    String deleteMember(String memberId);
    Object registerStore(String memberId, StoreRequestDto storeRequestDto);

    Object getMemberInfo(String memberId);

    Object updateMemberInfo(String memberId, MemberInfoRequestDto memberInfoRequestDto);
}
