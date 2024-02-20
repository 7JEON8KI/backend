package com.hyundai.domain.login.security;


import com.hyundai.global.config.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * author : 변형준
 * fileName : JwtProvider
 * since : 2/11/24
 */
@Slf4j
@Setter
@Getter
@Component
@RequiredArgsConstructor
public class JwtProvider {


    private final ApplicationProperties applicationProperties;

    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24;        // 1day
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;    // 1week
    private final String AUTHORITIES_KEY = "role";

    private final CustomMemberDetailsService customMemberDetailsService;
    /*
     * 토큰 생성 메소드 jwt에 저장할 회원정보를 파라미터로 전달
     */
    private String createToken(Authentication authentication, Long expireLength) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireLength);

        CustomMemberDetails member = (CustomMemberDetails) authentication.getPrincipal();
        String memberId = member.getMemberId();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, applicationProperties.getJWT_SECRET_KEY())
                .setSubject(memberId)
                .claim(AUTHORITIES_KEY, role)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .compact();
    }

    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, ACCESS_TOKEN_EXPIRE_LENGTH);
    }

    public String createRefreshToken(Authentication authentication) {
        return createToken(authentication, REFRESH_TOKEN_EXPIRE_LENGTH);
    }

    public String findMemberId(String token) {
        String subject = String.valueOf(parseClaims(token).getSubject());
        log.debug("findMemberId() :: " + subject);
        return subject;
    }

    public Authentication getAuthentication(String token) {
        log.debug("getAuthentication() :: " + token);
        UserDetails userDetails = customMemberDetailsService.loadUserByMemberId(findMemberId(token));
        return new UsernamePasswordAuthenticationToken(userDetails,
                "",
                userDetails.getAuthorities());
    }

    public JwtResultType validateToken(String token) {
        try {
            log.debug("토큰 검증 시작 : " + token);
            Jwts.parser().setSigningKey(applicationProperties.getJWT_SECRET_KEY()).parseClaimsJws(token);
            return JwtResultType.VALID_JWT;
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT 토큰입니다.");
            return JwtResultType.EXPIRED_JWT;
        } catch (Exception e) {
            log.debug("JWT 토큰이 잘못되었습니다");
            return JwtResultType.INVALID_JWT;
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            log.debug("parseClaims() :: " + accessToken);
            return Jwts.parser().setSigningKey(applicationProperties.getJWT_SECRET_KEY()).parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}

