package com.hyundai.global.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyundai.domain.login.dto.oauth.OAuthMember;
import com.hyundai.domain.login.security.JwtProvider;
import com.hyundai.domain.login.service.kakao.KakaoClient;
import com.hyundai.global.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : 변형준
 * @fileName : TokenInterceptor
 * @since : 2/13/24
 */

@Log4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;
    private final KakaoClient kakaoClient;
    private final MemberMapper memberMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("accessToken");
        log.debug("넘겨받은 accessToken :: " + accessToken);
        // accessToken을 이용해 카카오 API를 호출하여 email을 가져오는 로직
        String memberEmail = callKakaoApi(accessToken);
        log.debug("카카오 API로부터 받은 email :: " + memberEmail);
        // email을 이용해 memberId를 가져오는 로직
        String memberId = getMemberIdByEmail(memberEmail);
        log.debug("DB로부터 받은 memberId :: " + memberId);
        // memberId를 request에 속성으로 저장
        request.setAttribute("memberId", memberId);
        return true;
    }

    // accessToken을 이용해 카카오 API를 호출하여 email을 가져오는 메소드
    private String callKakaoApi(String accessToken) throws JsonProcessingException {
        // 카카오 API 호출 로직
        accessToken = jwtProvider.getAccessToken(accessToken);
        OAuthMember oAuthMember = kakaoClient.getMemberInfo(accessToken);
        return oAuthMember.getEmail();
    }

    // email을 이용해 memberId를 가져오는 메소드
    private String getMemberIdByEmail(String memberEmail) {
        return memberMapper.findMemberByEmail(memberEmail).getMemberId();
    }
}
