package com.hyundai.domain.login.security;

import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * fileName      : CustomAuthenticationEntryPoint
 * author        : 변형준
 * since         : 2/20/24
 * 내용           :
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        JSONObject json = new JSONObject();
        json.put("code", 401);
        json.put("message", authException.getLocalizedMessage());

        response.getWriter().print(json);
    }

}
