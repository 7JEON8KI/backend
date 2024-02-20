package com.hyundai.domain.login.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * fileName      : JwtFilter
 * author        : 변형준
 * since         : 2/20/24
 * 내용           :
 */

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final String TOKEN_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    // "/api/v1" 붙여서 작성
    private final List<String> EXCLUDE_URL_PATTERN = List.of(
            "/api/v1/test/all"
            , "/api/v1/auth/save"
            , "/api/v1/auth/login/kakao"
            , "/api/v1/swagger-ui.html"
            , "/api/v1/webjars"
            , "/api/v1/v2/api-docs"
            , "/api/v1/v3/api-docs"
            , "/api/v1/swagger-resources"
            , "/api/v1/reviews/product"

            );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        return EXCLUDE_URL_PATTERN.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        log.debug("token : " + token);
        if (StringUtils.hasText(token)) {
            JwtResultType jwtResultType = jwtProvider.validateToken(token);
            if (jwtResultType == JwtResultType.VALID_JWT) {
                log.debug("유효한 JWT 입니다.");
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else if (jwtResultType == JwtResultType.EXPIRED_JWT) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                JSONObject json = new JSONObject();
                json.put("code", 401);
                json.put("message", "Access Token 이 만료되었습니다.");
                response.getWriter().print(json);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                JSONObject json = new JSONObject();
                json.put("code", 400);
                json.put("message", "잘못된 JWT 입니다.");
                response.getWriter().print(json);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            JSONObject json = new JSONObject();
            json.put("code", 400);
            json.put("message", "JWT 가 존재하지 않습니다.");
            response.getWriter().print(json);
        }

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("bearerToken : " + bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
