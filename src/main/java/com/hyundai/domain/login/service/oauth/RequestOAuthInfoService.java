package com.hyundai.domain.login.service.oauth;

import com.hyundai.domain.login.dto.kakao.KakaoTokenResponseDto;
import com.hyundai.domain.login.dto.oauth.OAuthMember;
import com.hyundai.domain.login.dto.oauth.OAuthParams;
import com.hyundai.domain.login.entity.enumtype.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : 변형준
 * @fileName : RequestOAuthInfoService
 * @since : 2/11/24
 */

@Component
public class RequestOAuthInfoService {     //Enum = 키, Client = 값으로 저장하는 Map 생성
        private final Map<OAuthProvider, OAuthClient> clients;

        //생성과 동시에 클라이언트 주입
        public RequestOAuthInfoService(List<OAuthClient> clients) {
            this.clients = clients.stream().collect(
                    Collectors.toUnmodifiableMap(OAuthClient::oauthProvider, Function.identity()));
        }

        //넘겨받은 params의 enum 클래스와 동일한 객체를 주입
        public KakaoTokenResponseDto request(OAuthParams oAuthParams) {
            OAuthClient client = clients.get(oAuthParams.oAuthProvider());
            KakaoTokenResponseDto kakaoToken = client.getOauthLoginToken(oAuthParams);

            return kakaoToken;
        }
    }

