package com.hyundai.domain.login.dto.kakao;

import com.hyundai.domain.login.dto.oauth.OAuthParams;
import com.hyundai.domain.login.entity.enumtype.OAuthProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author : 변형준
 * @fileName : KakaoParams
 * @since : 2/11/24
 */
@Setter
@Getter
public class KakaoParams implements OAuthParams {
    // Controller에서 Post요청으로 전달된 파라미터
    private String authorizationCode;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO; // Enum 자료형 지정
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        // 필수로 포함되어야할 Body 작성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }

}

