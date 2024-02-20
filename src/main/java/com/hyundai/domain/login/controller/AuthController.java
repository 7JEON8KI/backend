package com.hyundai.domain.login.controller;

/**
 * @author : 변형준
 * @fileName : AuthController
 * @since : 2/11/24
 */

import com.hyundai.domain.login.dto.StoreRequestDto;
import com.hyundai.domain.login.dto.kakao.KakaoParams;
import com.hyundai.domain.login.dto.oauth.OAuthMemberRequestDto;
import com.hyundai.domain.login.security.JwtProvider;
import com.hyundai.domain.login.security.JwtProvider2;
import com.hyundai.domain.login.service.oauth.OAuthService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService oAuthService;

    @PostMapping("/login/kakao")
    public ResponseEntity handleKakaoLogin(@RequestBody KakaoParams kakaoParams){
        log.debug("넘겨받은 Kakao 인증키 :: " + kakaoParams.getAuthorizationCode());
        return ResponseMessage.SuccessResponse("카카오 로그인 성공", oAuthService.getMemberByOauthLogin(kakaoParams));
    }

    @PostMapping("/save")
    public ResponseEntity saveMember(@RequestHeader("refreshToken") String jwtRefreshToken, @RequestBody OAuthMemberRequestDto oAuthMemberRequestDto) {
        log.debug("넘겨받은 회원정보 :: " + oAuthMemberRequestDto.getMemberEmail());
        log.debug("넘겨받은 refreshToken :: " + jwtRefreshToken);
        return ResponseMessage.SuccessResponse("회원가입 성공",  oAuthService.saveMember(oAuthMemberRequestDto, jwtRefreshToken));
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity unlink(HttpServletRequest request) {
        String memberId = request.getAttribute("memberId").toString();
        String accessToken = request.getAttribute("accessToken").toString();
        log.debug("deleteUser:" + memberId);
        return ResponseMessage.SuccessResponse("회원탈퇴 성공", oAuthService.deleteMember(memberId, accessToken));
    }

    // 판매자 등록요청
    @PostMapping("/registerStore")
    public ResponseEntity registerStore(HttpServletRequest request, @RequestBody StoreRequestDto storeRequestDto){
        String memberId = request.getAttribute("memberId").toString();
        log.debug("넘겨받은 판매자 정보 :: " + storeRequestDto.getStoreName());
        return ResponseMessage.SuccessResponse("판매자 등록 요청 성공", oAuthService.registerStore(memberId, storeRequestDto));
    }
}

