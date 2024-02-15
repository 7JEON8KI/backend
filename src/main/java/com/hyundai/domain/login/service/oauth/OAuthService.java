package com.hyundai.domain.login.service.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyundai.domain.login.dto.kakao.KakaoLoginResponseDto;
import com.hyundai.domain.login.dto.kakao.KakaoTokenResponseDto;
import com.hyundai.domain.login.dto.oauth.OAuthMember;
import com.hyundai.domain.login.dto.oauth.OAuthMemberRequestDto;
import com.hyundai.domain.login.dto.oauth.OAuthParams;
import com.hyundai.domain.login.entity.Member;
import com.hyundai.domain.login.entity.enumtype.Role;
import com.hyundai.domain.login.security.JwtProvider;
import com.hyundai.domain.login.service.kakao.KakaoClient;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : 변형준
 * @fileName : OauthService
 * @since : 2/11/24
 */
@Log4j
@RequiredArgsConstructor
@Component
public class OAuthService {

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

        boolean isMember = false;

        // accessToken 과 refreshToken  JWT 암호화
        log.debug("------ JWT 발급 ------");
        String accessToken = jwtProvider.createAccessToken(kakaoTokenResponseDto);
        String refreshToken = jwtProvider.createRefreshToken(kakaoTokenResponseDto);
        log.debug("------ JWT 발급완료 ------");

        if (accessMember.isPresent()) {
            isMember = true;
            Map<String, Object> map = new HashMap<>();
            map.put("memberId", accessMember.get().getMemberId());
            map.put("refreshToken", refreshToken);
            memberMapper.updateRefreshToken(map);
        }

        return KakaoLoginResponseDto.builder()
                .memberEmail(oAuthMember.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isMember(isMember)
                .build();
    }

    public String saveMember(OAuthMemberRequestDto oAuthMemberRequestDto, String jwtRefreshToken) throws JsonProcessingException {
        log.debug("------ 회원가입 ------");
        memberMapper.findMemberByEmail(oAuthMemberRequestDto.getMemberEmail()).ifPresent(
                member -> {
                    throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
                }
        );

        Map<String, Object> params = new HashMap<>();
        params.put("memberId", UUID.randomUUID().toString());
        params.put("memberEmail", oAuthMemberRequestDto.getMemberEmail());
        params.put("memberName", oAuthMemberRequestDto.getMemberName());
        params.put("memberPhone", oAuthMemberRequestDto.getMemberPhone());
        params.put("memberNickname", oAuthMemberRequestDto.getMemberNickname());
        params.put("memberImage", oAuthMemberRequestDto.getMemberImage());
        params.put("memberGender", oAuthMemberRequestDto.getMemberGender());
        params.put("memberBirth", oAuthMemberRequestDto.getMemberBirth());
        params.put("memberRole", Role.ROLE_USER);
        params.put("infoAddr", oAuthMemberRequestDto.getInfoAddr());
        params.put("infoZipcode", oAuthMemberRequestDto.getInfoZipcode());
        params.put("refreshToken", jwtRefreshToken);
        memberMapper.insertMemberAndInfo(params);
        log.debug("------ 회원가입 완료 ------");
        return oAuthMemberRequestDto.getMemberName();
    }

    public String deleteMember(String memberId, String accessToken) {
        log.debug("------ 회원 탈퇴 ------");
        Member deleteMember = Member.builder()
                .memberId(memberId)
                .build();
        memberMapper.deleteMember(memberId);
        //카카오 연결 끊기
        kakaoClient.unlink(accessToken);
        log.debug("------ 회원 탈퇴 완료 ------");
        return memberMapper.findMemberByMemberId(memberId).getMemberName();
    }

}
