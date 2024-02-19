package com.hyundai.global.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyundai.domain.login.dto.oauth.OAuthMember;
import com.hyundai.domain.login.entity.enumtype.Role;
import com.hyundai.domain.login.security.JwtProvider;
import com.hyundai.domain.login.service.kakao.KakaoClient;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> map = callKakaoApi(accessToken);
        String memberEmail = map.get("memberEmail").toString();
        request.setAttribute("memberEmail", memberEmail);
        accessToken = map.get("accessToken").toString();
        request.setAttribute("accessToken", accessToken);
        log.debug("카카오 API로부터 받은 email :: " + memberEmail);
        // email을 이용해 memberId를 가져오는 로직
        String memberId = getMemberIdByEmail(memberEmail);
        log.debug("DB로부터 받은 memberId :: " + memberId);
        // memberId를 request에 속성으로 저장
        request.setAttribute("memberId", memberId);
        Role memberRole = getMemberRoleByEmail(memberEmail);
        log.debug("DB로부터 받은 Role :: " + memberRole);
        request.setAttribute("memberRole", memberRole);
        return true;
    }

    // accessToken을 이용해 카카오 API를 호출하여 email을 가져오는 메소드
    private Map<String, Object> callKakaoApi(String accessToken) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        // 카카오 API 호출 로직
        accessToken = jwtProvider.getAccessToken(accessToken);
        map.put("accessToken", accessToken);
        log.debug("토큰 디코딩 결과 :: " + accessToken);
        OAuthMember oAuthMember = kakaoClient.getMemberInfo(accessToken);
        map.put("memberEmail", oAuthMember.getEmail());
        log.debug("카카오 API로부터 받은 email :: " + oAuthMember.getEmail());
        return map;
    }

    // email을 이용해 memberId를 가져오는 메소드
    private String getMemberIdByEmail(String memberEmail) {
        return memberMapper.findMemberByEmail(memberEmail).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL)
        ).getMemberId();
    }
    private Role getMemberRoleByEmail(String memberEmail) {
        return memberMapper.findMemberByEmail(memberEmail).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL)
        ).getMemberRole();
    }
}
