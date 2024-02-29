package com.hyundai.domain.login.controller;


import com.hyundai.domain.login.dto.MemberInfoRequestDto;
import com.hyundai.domain.login.dto.StoreRequestDto;
import com.hyundai.domain.login.dto.kakao.KakaoParams;
import com.hyundai.domain.login.dto.oauth.OAuthMemberRequestDto;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.login.service.oauth.OAuthService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * author : 변형준
 * fileName : AuthController
 * since : 2/11/24
 */

@Slf4j
@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService oAuthService;

    @PostMapping("/login/kakao")
    public ResponseEntity<?> handleKakaoLogin(@RequestBody KakaoParams kakaoParams){
        log.debug("넘겨받은 Kakao 인증키 :: " + kakaoParams.getAuthorizationCode());
        return ResponseMessage.SuccessResponse("카카오 로그인 성공", oAuthService.getMemberByOauthLogin(kakaoParams));
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMember(@RequestBody OAuthMemberRequestDto oAuthMemberRequestDto) {
        log.debug("넘겨받은 회원정보 :: " + oAuthMemberRequestDto.getMemberEmail());
        return ResponseMessage.SuccessResponse("회원가입 성공",  oAuthService.saveMember(oAuthMemberRequestDto));
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity<?> unlink() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("deleteUser:" + memberId);
        return ResponseMessage.SuccessResponse("회원탈퇴 성공", oAuthService.deleteMember(memberId));
    }

    // 판매자 등록요청
    @PostMapping("/registerStore")
    public ResponseEntity<?> registerStore(@RequestBody StoreRequestDto storeRequestDto){
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("넘겨받은 판매자 정보 :: " + storeRequestDto.getStoreName());
        return ResponseMessage.SuccessResponse("판매자 등록 요청 성공", oAuthService.registerStore(memberId, storeRequestDto));
    }

    @GetMapping("/member")
    public ResponseEntity<?> getMemberInfo() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("getMemberInfo:" + memberId);
        return ResponseMessage.SuccessResponse("회원정보 조회 성공", oAuthService.getMemberInfo(memberId));
    }

    // 멤버 정보 수정
    @PutMapping("/member")
    public ResponseEntity<?> updateMemberInfo(@RequestBody MemberInfoRequestDto memberInfoRequestDto) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("updateMemberInfo:" + memberId);
        oAuthService.updateMemberInfo(memberId, memberInfoRequestDto);
        return ResponseMessage.SuccessResponse("회원정보 수정 성공", "");
    }
}

