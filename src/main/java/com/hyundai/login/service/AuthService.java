package com.hyundai.login.service;

import com.hyundai.login.domain.Member;
import com.hyundai.login.domain.enumtype.Role;
import com.hyundai.login.security.JwtTokenProvider;
import com.hyundai.login.security.KakaoOauthClient;
import com.hyundai.login.security.dto.LoginRequestDto;
import com.hyundai.login.security.dto.OAuthType;
import com.hyundai.login.mapper.MemberMapper;
import com.hyundai.login.security.dto.LoginResponseDto;
import com.hyundai.login.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Log4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final KakaoOauthClient oAuthClient;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final MemberMapper memberMapper;
    private long accessTokenValidity = Long.MAX_VALUE;
    private String bearerHeader = "Bearer ";

    public LoginResponseDto oAuthLogin(LoginRequestDto loginRequestDto) {
        String email = oAuthClient.getEmail(loginRequestDto);
        log.info("이메일 정보 얻어오기 성공 oAuthLogin : " + email);

        Member findMember = memberMapper.findMemberByEmail(email);
        if (findMember != null) {
            log.info("DB에 저장된 회원이라면 AT,RT만 반송");
            return getUpdatedToken(findMember);
        }
        log.info("첫 회원이라면 가입하기");
        return joinByEmail(email);
    }

    private LoginResponseDto getUpdatedToken(Member member) {
        String newAccessToken = updateAccessToken(member);
        String refreshToken = member.getRefreshToken();

        return LoginResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .isFirstLogin(false)
                .build();
    }

    public LoginResponseDto joinByEmail(String email) {
        String memberId = UUID.randomUUID().toString();
        TokenDto tokenDto = jwtTokenProvider.createAccessAndRefreshTokenDto(memberId);
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();

        Member member = Member.builder()
                .memberId(memberId)
                .memberEmail(email)
                .refreshToken(refreshToken)
                .memberRole(Role.ROLE_USER)
                .build();
        log.info("첫 로그인 - 회원 가입" + memberId);

        memberMapper.saveMember(member);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isFirstLogin(true)
                .build();
    }

    private String updateAccessToken(Member member) {
        String memberId = member.getMemberId();
        String email = member.getMemberEmail();
        String refreshToken = member.getRefreshToken();
        String newAccessToken = jwtTokenProvider.createBearerTokenWithValidity(memberId, accessTokenValidity);

        validateRefreshToken(memberId, email);
        return newAccessToken;
    }

    private void validateRefreshToken(String memberId, String email) {
        Member findMember = memberMapper.findMemberByEmail(email);
        if (findMember == null) {
            throw new NoSuchElementException("해당 회원이 존재하지 않습니다.");
        }

        boolean isValidMember = findMember.getMemberId().equals(memberId);
        if (!isValidMember) {
            throw new NoSuchElementException("해당 회원의 리프레시 토큰이 유효하지 않습니다.");
        }
    }
}