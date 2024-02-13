package com.hyundai.domain.login.controller;

/**
 * @author : 변형준
 * @fileName : LoginController
 * @since : 2/11/24
 */

import com.hyundai.domain.login.dto.kakao.KakaoParams;
import com.hyundai.domain.login.security.JwtProvider;
import com.hyundai.domain.login.service.MemberService;
import com.hyundai.domain.login.service.oatuh.OAuthService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/login/kakao")
    public ResponseEntity<String> handleKakaoLogin(@RequestBody KakaoParams kakaoParams){
        log.debug("넘겨받은 Kakao 인증키 :: " + kakaoParams.getAuthorizationCode());

        String accessToken = oAuthService.getMemberByOauthLogin(kakaoParams);
        //응답 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("accessToken", accessToken);

        return ResponseEntity.ok().headers(headers).body("success");
    }

    @PostMapping("/login/first")
    public ResponseEntity<String> handleKakaoLoginFirst(@RequestBody KakaoParams kakaoParams){
        log.debug("넘겨받은 Kakao 인증키 :: " + kakaoParams.getAuthorizationCode());

        String accessToken = oAuthService.getMemberByOauthLogin(kakaoParams);
        //응답 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("accessToken", accessToken);

        return ResponseEntity.ok().headers(headers).body("success");
    }


//    //로그아웃
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(String jwtToken) {
//        String memberId = jwtProvider.getSubject(jwtToken);
//
//        //db 삭제
//        log.debug("logout...");
//        oAuthService.logout(memberId);
//
//        return ResponseEntity.ok().body("success");
//    }


    @DeleteMapping("/deleteMember")
    public ResponseEntity<String> unlink(String jwtToken) {
        String memberId = jwtProvider.getSubject(jwtToken);

        //db 삭제
        log.debug("deleteUser...");
        oAuthService.deleteMember(memberId);

        return ResponseEntity.ok().body("success");
    }


}

