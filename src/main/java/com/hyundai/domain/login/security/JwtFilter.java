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
            , "/api/v1/reviews/products"
            );

    // "/api/v1" 붙여서 작성, 특정 URI에서는 에러 응답을 보내지 않음
    private final List<String> EXCLUDE_CONTINUE_URL_PATTERN = List.of(
        "/api/v1/products", "/api/v1/recommendation"
    );

    private boolean isSkippableUri(String requestUri) {
        return EXCLUDE_CONTINUE_URL_PATTERN.stream().anyMatch(requestUri::startsWith);
    }


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
            } else if (jwtResultType == JwtResultType.EXPIRED_JWT || jwtResultType == JwtResultType.INVALID_JWT) {
                // 특정 URI에서는 에러 응답을 보내지 않고, 필터 체인을 계속 진행
                if (isSkippableUri(request.getRequestURI())) {
                    log.debug("JWT 가 만료되었거나 잘못된 JWT 입니다. (에러 응답을 보내지 않음)");
                } else {
                    sendErrorResponse(response, jwtResultType);
                    return; // 추가 처리 중단
                }
            }
            filterChain.doFilter(request, response);
        } else if (!isSkippableUri(request.getRequestURI())) {
            sendErrorResponse(response, JwtResultType.NO_JWT);
            return; // 추가 처리 중단
        } else {
            log.debug("JWT 가 없습니다. (에러 응답을 보내지 않음)");
            filterChain.doFilter(request, response);
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

    private void sendErrorResponse(HttpServletResponse response, JwtResultType resultType) throws IOException {
        int statusCode = resultType == JwtResultType.EXPIRED_JWT ? HttpServletResponse.SC_UNAUTHORIZED : HttpServletResponse.SC_BAD_REQUEST;
        String message = resultType == JwtResultType.EXPIRED_JWT ? "Access Token 이 만료되었습니다." : "잘못된 JWT 입니다.";

        response.setStatus(statusCode);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        json.put("code", statusCode);
        json.put("message", message);
        response.getWriter().print(json);
    }
}
