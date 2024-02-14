package com.hyundai.domain.login.controller;

/**
 * @author : 변형준
 * @fileName : AuthController
 * @since : 2/11/24
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyundai.domain.login.dto.kakao.KakaoLoginResponseDto;
import com.hyundai.domain.login.dto.kakao.KakaoParams;
import com.hyundai.domain.login.dto.oauth.OAuthMemberRequestDto;
import com.hyundai.domain.login.security.JwtProvider;
import com.hyundai.domain.login.service.oauth.OAuthService;
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

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/login/kakao")
    public ResponseEntity<KakaoLoginResponseDto> handleKakaoLogin(@RequestBody KakaoParams kakaoParams){
        log.debug("넘겨받은 Kakao 인증키 :: " + kakaoParams.getAuthorizationCode());

        KakaoLoginResponseDto kakaoLoginResponseDto = oAuthService.getMemberByOauthLogin(kakaoParams);

        return ResponseEntity.ok().body(kakaoLoginResponseDto);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMember(@RequestHeader("refreshToken") String jwtRefreshToken, @RequestBody OAuthMemberRequestDto oAuthMemberRequestDto) throws JsonProcessingException {
        log.debug("넘겨받은 회원정보 :: " + oAuthMemberRequestDto.getMemberEmail());
        log.debug("넘겨받은 refreshToken :: " + jwtRefreshToken);
        boolean result = oAuthService.saveMember(oAuthMemberRequestDto, jwtRefreshToken);
        if(result){
            return ResponseEntity.ok().body("success");
        }else{
            return ResponseEntity.ok().body("fail");
        }
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity<String> unlink(HttpServletRequest request) {
        String memberId = request.getAttribute("memberId").toString();
        String accessToken = request.getAttribute("accessToken").toString();
        //db 삭제
        log.debug("deleteUser:" + memberId);
        oAuthService.deleteMember(memberId, accessToken);

        return ResponseEntity.ok().body("success");
    }
}

