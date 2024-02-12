package com.hyundai.domain.login.dto.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hyundai.domain.login.dto.oauth.OAuthMember;
import com.hyundai.domain.login.entity.enumtype.OAuthProvider;
import lombok.Getter;

/**
 * @author : 변형준
 * @fileName : KakaoMember
 * @since : 2/11/24
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoMember implements OAuthMember {

    @JsonProperty("kakao_account") // 응답 정보와 동일한 이름의 property 매핑
    private KakaoAccount kakao_account; // response와 데이터 매핑을 위한 _사용

    //데이터 반환값을 받을 내장클래스
    //필요한 값만 추출하기 위해서 @JsonIgnoreProperties 사용
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount{
        private String email;

    }

    @Override
    public String getEmail() {
        return kakao_account.email;
    }


    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
