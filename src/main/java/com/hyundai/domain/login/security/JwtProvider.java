package com.hyundai.domain.login.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.domain.login.dto.kakao.KakaoTokenResponseDto;
import com.hyundai.global.config.ApplicationProperties;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author : 변형준
 * @fileName : JwtProvider
 * @since : 2/11/24
 */
@Slf4j
@Setter
@Getter
@Component
public class JwtProvider {

    @Autowired
    private ApplicationProperties applicationProperties;

    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60;        // 1hour
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;    // 1week
    private final String AUTHORITIES_KEY = "role";

    @Autowired
    private CustomMemberDetailsService customMemberDetailsService;
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


    public String createAccessToken(KakaoTokenResponseDto kakaoTokenResponseDto) {
        Date now = new Date(System.currentTimeMillis());
        Long expiration = 10 * 24 * 60 * 60 * 1000L; //만료기한 설정 시 사용

        log.debug("비밀키 " + applicationProperties.getJWT_SECRET_KEY());
        /* 토큰이 보관할 회원ID */
        Claims claims = Jwts.claims();
        claims.put("AccessToken", kakaoTokenResponseDto.getAccessToken()); //비공개 클레임 등록
        return Jwts.builder().setHeaderParam("typ", "JWT") // 토큰 타입 지정
                .setSubject("jwtToken") // 토큰 제목
                .setIssuedAt(now) // 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 만료기한
                .setClaims(claims) // 회원 아이디 저장(비공개 클레임)
                .signWith(SignatureAlgorithm.HS256, applicationProperties.getJWT_SECRET_KEY()) //해싱알고리즘과 시크릿 키
                .compact(); //토큰 직렬화
    }
    public String createRefreshToken(KakaoTokenResponseDto kakaoTokenResponseDto) {
        Date now = new Date(System.currentTimeMillis());
        Long expiration = 10 * 24 * 60 * 60 * 1000L; //만료기한 설정 시 사용

        log.debug("비밀키 " + applicationProperties.getJWT_SECRET_KEY());
        /* 토큰이 보관할 회원ID */
        Claims claims = Jwts.claims();
        claims.put("refreshToken", kakaoTokenResponseDto.getRefreshToken()); //비공개 클레임 등록
        return Jwts.builder().setHeaderParam("typ", "JWT") // 토큰 타입 지정
                .setSubject("jwtToken") // 토큰 제목
                .setIssuedAt(now) // 발급시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 만료기한
                .setClaims(claims) // 회원 아이디 저장(비공개 클레임)
                .signWith(SignatureAlgorithm.HS256, applicationProperties.getJWT_SECRET_KEY()) //해싱알고리즘과 시크릿 키
                .compact(); //토큰 직렬화
    }
    public String getAccessToken(String token) throws JsonProcessingException {
        log.debug("비밀키 " + applicationProperties.getJWT_SECRET_KEY());
        String payload = token.split("\\.")[1]; // 토큰의 페이로드 추출
        Base64.Decoder decoder = Base64.getDecoder();
        payload = new String(decoder.decode(payload)); // 페이로드 디코딩

        log.debug("해독된 토큰:: " + payload);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonPayload = objectMapper.readTree(payload);
        String accessToken = jsonPayload.get("AccessToken").asText();
        log.debug("accessToken:: " + accessToken);
        return accessToken;
    }
    public String getRefreshToken(String token) throws JsonProcessingException {
        log.debug("비밀키 " + applicationProperties.getJWT_SECRET_KEY());
        String payload = token.split("\\.")[1]; // 토큰의 페이로드 추출
        Base64.Decoder decoder = Base64.getDecoder();
        payload = new String(decoder.decode(payload)); // 페이로드 디코딩

        log.debug("해독된 토큰:: " + payload);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonPayload = objectMapper.readTree(payload);
        String refreshToken = jsonPayload.get("refreshToken").asText();
        log.debug("refreshToken:: " + refreshToken);
        return refreshToken;
    }


    /* 유효성 확인(해독된 jwt) */
    public boolean vaildToken(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(applicationProperties.getJWT_SECRET_KEY()))
                    .parseClaimsJwt(jwt) //해독된 토큰 파싱
                    .getBody();
            return true;  //유효하다면 true 반환
        } catch (Exception e) {
            log.debug("유효하지 않은 토큰입니다.");
            return false; //유효하지 않다면 false 반환
        }
    }

}

