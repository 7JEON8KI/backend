package com.hyundai.domain.login.service.oauth;


import com.hyundai.domain.login.dto.MemberInfoRequestDto;
import com.hyundai.domain.login.dto.StoreRequestDto;
import com.hyundai.domain.login.dto.kakao.KakaoLoginResponseDto;
import com.hyundai.domain.login.dto.kakao.KakaoTokenResponseDto;
import com.hyundai.domain.login.dto.oauth.OAuthMember;
import com.hyundai.domain.login.dto.oauth.OAuthMemberRequestDto;
import com.hyundai.domain.login.dto.oauth.OAuthParams;
import com.hyundai.domain.login.entity.Member;
import com.hyundai.domain.login.entity.enumtype.Role;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.login.security.JwtProvider;
import com.hyundai.domain.login.service.kakao.KakaoClient;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * author : 변형준
 * fileName : OauthService
 * since : 2/11/24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final MemberMapper memberMapper;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtProvider jwtProvider;
    private final KakaoClient kakaoClient;
    // 받아온 유저정보로 로그인 시도
    public KakaoLoginResponseDto getMemberByOauthLogin(OAuthParams oAuthParams) {
        log.debug("------ Oauth 로그인 시도 ------");

        KakaoTokenResponseDto kakaoTokenResponseDto = requestOAuthInfoService.request(oAuthParams);
        OAuthMember oAuthMember = kakaoClient.getMemberInfo(kakaoTokenResponseDto.getAccessToken());
        log.debug("전달받은 유저정보:: " + oAuthMember.getEmail());
        // 획득된 회원정보 DB 조회
        Optional<Member> accessMember = memberMapper.findMemberByEmail(oAuthMember.getEmail());

        log.debug("test:: " + accessMember.isPresent());
        if(accessMember.isPresent()) {
            CustomMemberDetails userDetails = CustomMemberDetails.create(accessMember.get());

            // 사용자 정보를 기반으로 Authentication 객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // SecurityContext에 Authentication 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // accessToken 과 refreshToken  JWT 암호화
            log.debug("------ JWT 발급 ------");
            String accessToken = jwtProvider.createAccessToken(authentication);
            String refreshToken = jwtProvider.createRefreshToken(authentication);
            log.debug("------ JWT 발급완료 ------");

            Map<String, Object> map = new HashMap<>();
            map.put("memberId", accessMember.get().getMemberId());
            map.put("refreshToken", refreshToken);
            memberMapper.updateRefreshToken(map);

            return KakaoLoginResponseDto.builder()
                    .memberEmail(oAuthMember.getEmail())
                    .accessToken(accessToken)
                    .isMember(true)
                    .build();
        } else {
            return KakaoLoginResponseDto.builder()
                    .memberEmail(oAuthMember.getEmail())
                    .accessToken(null)
                    .isMember(false)
                    .build();
        }
    }

    public String saveMember(OAuthMemberRequestDto oAuthMemberRequestDto) {
        log.debug("------ 회원가입 ------");
        memberMapper.findMemberByEmail(oAuthMemberRequestDto.getMemberEmail()).ifPresent(
                member -> {
                    throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
                }
        );
        String memberId = UUID.randomUUID().toString();
        while(memberMapper.findMemberByMemberId(memberId).isPresent()) {
            memberId = UUID.randomUUID().toString();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("memberEmail", oAuthMemberRequestDto.getMemberEmail());
        params.put("memberName", oAuthMemberRequestDto.getMemberName());
        params.put("memberPhone", oAuthMemberRequestDto.getMemberPhone());
        params.put("memberNickname", oAuthMemberRequestDto.getMemberNickname());
        params.put("memberGender", oAuthMemberRequestDto.getMemberGender());
        params.put("memberBirth", oAuthMemberRequestDto.getMemberBirth());
        params.put("memberRole", Role.ROLE_MEMBER);
        params.put("refreshToken", "null");
        params.put("infoAddr", oAuthMemberRequestDto.getInfoAddr());
        params.put("infoZipcode", oAuthMemberRequestDto.getInfoZipcode());

        memberMapper.insertMemberAndInfo(params);
        log.debug("------ 회원가입 완료 ------");
        return oAuthMemberRequestDto.getMemberName();
    }

    public String deleteMember(String memberId) {
        log.debug("------ 회원 탈퇴 ------");
        memberMapper.deleteMember(memberId);
        //카카오 연결 끊기
//        kakaoClient.unlink(accessToken);
        log.debug("------ 회원 탈퇴 완료 ------");
        Member member = memberMapper.findMemberByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_NOT_FOUND));
        return member.getMemberName();
    }

    public Object registerStore(String memberId, StoreRequestDto storeRequestDto) {
        log.debug("------ 판매자 등록 요청 ------");
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("storeName", storeRequestDto.getStoreName());
        params.put("storeTel", storeRequestDto.getStoreTel());
        memberMapper.registerStore(params);
        log.debug("------ 판매자 등록 요청 완료 ------");
        return storeRequestDto.getStoreName();
    }

    @Override
    public Object getMemberInfo(String memberId) {
        log.debug("------ 회원정보 조회 ------");
        return memberMapper.getMemberByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public Object updateMemberInfo(String memberId, MemberInfoRequestDto memberInfoRequestDto) {
        log.debug("------ 회원정보 수정 ------");
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("memberName", memberInfoRequestDto.getMemberName());
        params.put("memberNickname", memberInfoRequestDto.getMemberNickname());
        params.put("memberPhone", memberInfoRequestDto.getMemberPhone());
        params.put("memberGender", memberInfoRequestDto.getMemberGender());
        params.put("memberBirth", memberInfoRequestDto.getMemberBirth());
        params.put("infoAddr", memberInfoRequestDto.getInfoAddr());
        params.put("infoZipcode", memberInfoRequestDto.getInfoZipcode());
        memberMapper.updateMember(params);
        memberMapper.updateMemberInfo(params);
        log.debug("------ 회원정보 수정 완료 ------");
        return memberInfoRequestDto;
    }
}
