package com.hyundai.domain.login.security;

import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final String TOKEN_PREFIX = "Bearer ";
    private final JwtProvider2 jwtProvider;
    private final List<String> EXCLUDE_URL_PATTERN = List.of("/auth/login/kakao", "/api/v1/signup", "/api/v1/refreshToken", "/api/v1/auth/login/kakao");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String pathName = request.getServletPath();
        return EXCLUDE_URL_PATTERN.stream().anyMatch(url -> {
            if (url.endsWith("/**")) {
                return pathName.startsWith(url.substring(0, url.length() - 3));
            } else {
                return url.equalsIgnoreCase(pathName);
            }
        });
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtFromRequest(request);

        if (StringUtils.hasText(token)) {

            JwtResultType jwtResultType = jwtProvider.validateToken(token);
            if (jwtResultType == JwtResultType.VALID_JWT) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
            } else if (jwtResultType == JwtResultType.EXPIRED_JWT) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");

                JSONObject json = new JSONObject();
                json.put("code", 401);
                json.put("message", "Access Token 이 만료되엇습니다.");
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
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
