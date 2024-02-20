package com.hyundai.domain.login.security;

import net.minidev.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
/**
 * fileName      : CustomDeniedHandler
 * author        : 변형준
 * since         : 2/20/24
 * 내용           :
 */
@Component
public class CustomDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        JSONObject json = new JSONObject();
        json.put("code", 403);
        json.put("message", accessDeniedException.getLocalizedMessage());

        response.getWriter().print(json);
    }
}
