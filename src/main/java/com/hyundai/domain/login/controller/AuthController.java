package com.hyundai.domain.login.controller;

/**
 * @author : 변형준
 * @fileName : AuthController
 * @since : 2/11/24
 */

import com.hyundai.domain.login.dto.StoreRequestDto;
import com.hyundai.domain.login.dto.kakao.KakaoParams;
import com.hyundai.domain.login.dto.oauth.OAuthMemberRequestDto;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.login.security.CustomMemberDetailsService;
import com.hyundai.domain.login.service.oauth.OAuthService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity saveMember(@RequestBody OAuthMemberRequestDto oAuthMemberRequestDto) {
        log.debug("넘겨받은 회원정보 :: " + oAuthMemberRequestDto.getMemberEmail());
        return ResponseMessage.SuccessResponse("회원가입 성공",  oAuthService.saveMember(oAuthMemberRequestDto));
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity unlink(HttpServletRequest request) {
        CustomMemberDetails cu =(CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = cu.getMemberId();
        log.debug(cu.getMemberId());
        log.debug("deleteUser:" + memberId);
        return ResponseMessage.SuccessResponse("회원탈퇴 성공", oAuthService.deleteMember(memberId));
    }

    // 판매자 등록요청
    @PostMapping("/registerStore")
    public ResponseEntity registerStore(HttpServletRequest request, @RequestBody StoreRequestDto storeRequestDto){
        String memberId = request.getAttribute("memberId").toString();
        log.debug("넘겨받은 판매자 정보 :: " + storeRequestDto.getStoreName());
        return ResponseMessage.SuccessResponse("판매자 등록 요청 성공", oAuthService.registerStore(memberId, storeRequestDto));
    }
}

