package com.hyundai.domain.login.controller;

/**
 * @author : 변형준
 * @fileName : AuthController
 * @since : 2/11/24
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyundai.domain.login.dto.kakao.KakaoParams;
import com.hyundai.domain.login.dto.oauth.OAuthMemberRequestDto;
import com.hyundai.domain.login.service.oauth.OAuthService;
import com.hyundai.global.message.ResponseMessage;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j
@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=utf8")
public class AuthController {

    @Autowired
    private OAuthService oAuthService;

    @PostMapping("/login/kakao")
    public ResponseEntity handleKakaoLogin(@RequestBody KakaoParams kakaoParams){
        log.debug("넘겨받은 Kakao 인증키 :: " + kakaoParams.getAuthorizationCode());
        return ResponseMessage.SuccessResponse("카카오 로그인 성공", oAuthService.getMemberByOauthLogin(kakaoParams));
    }

    @PostMapping("/save")
    public ResponseEntity saveMember(@RequestHeader("refreshToken") String jwtRefreshToken, @RequestBody OAuthMemberRequestDto oAuthMemberRequestDto) throws JsonProcessingException {
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
}

