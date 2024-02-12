package com.hyundai.domain.login.service.oatuh;

import com.hyundai.domain.login.dto.MemberDto;
import com.hyundai.domain.login.dto.oauth.OAuthMember;
import com.hyundai.domain.login.dto.oauth.OAuthParams;
import com.hyundai.domain.login.entity.Member;
import com.hyundai.domain.login.entity.enumtype.Role;
import com.hyundai.domain.login.security.JwtProvider;
import com.hyundai.global.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

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




    // 받아온 유저정보로 로그인 시도
    public String getMemberByOauthLogin(OAuthParams oAuthParams) {
        log.debug("------ Oauth 로그인 시도 ------");

        // 인증 파라미터 객체를 이용하여 해당 enum클래스에 해당하는 메소드 수행
        OAuthMember oAuthMember = requestOAuthInfoService.request(oAuthParams);
        log.debug("전달받은 유저정보:: " + oAuthMember.getEmail());

        // 획득한 회원정보로 검증할 MemberDTO 생성

        MemberDto memberDto = MemberDto.builder()
                .memberEmail(oAuthMember.getEmail())
                .build();

        // 획득된 회원정보 DB 조회
        Member accessMember = memberMapper.findMemberByEmail(oAuthMember.getEmail());



        if (accessMember == null) {
            memberDto = MemberDto.builder()
                    .memberId(UUID.randomUUID().toString())
                    .memberEmail(oAuthMember.getEmail())
                    .memberRole(Role.ROLE_USER)
                    .build();
            accessMember = memberDto.toEntity();
            log.debug("------ 회원가입 필요한 회원 ------");
            // 회원가입이 되지 않은 회원이기 때문에 회원 DTO에 값을 전달하여 DB저장
            log.debug("회원가입 요청 :: " + accessMember.getMemberEmail());
            // kakaoMember에서 전달된 데이터를 가진 memberDTO DB 저장
            memberMapper.insertMember(accessMember);

            log.debug("회원가입 완료 :: " + accessMember.getMemberEmail());
        } else {
            if(accessMember.getDeletedAt() != null) {
                log.debug("------ 탈퇴한 회원 ------");
                // 탈퇴한 회원이기 때문에 회원 DTO에 값을 전달하여 DB저장
                log.debug("재가입 요청 :: " + accessMember.getMemberEmail());
                Member backMember = Member.builder()
                        .memberId(accessMember.getMemberId())
                        .memberNickname("탈퇴한 회원")
                        .build();

                memberMapper.updateDeletedMember(backMember);

                log.debug("재가입 완료 :: " + accessMember.getMemberEmail());
            }
        }
        // 이미 가입된 회원은 토큰발급
        log.debug("------ JWT 발급 ------");

        // 반환할 JWT
        String accessJwt = jwtProvider.createToken(accessMember.getMemberId());

        log.debug("------ JWT 발급완료 ------");
        return accessJwt;
    }

    public String deleteMember(String memberId) {
        log.debug("------ 회원 탈퇴 ------");
        Member deleteMember = Member.builder()
                .memberId(memberId)
                .build();
        memberMapper.deleteMember(memberId);
        log.debug("------ 회원 탈퇴 완료 ------");
        return "회원 탈퇴 완료";
    }


}
