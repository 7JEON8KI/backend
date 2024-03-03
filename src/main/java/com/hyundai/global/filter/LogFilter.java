package com.hyundai.global.filter;

import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.global.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 강은구
 * @fileName : LogFilter
 * @description :
 * @since : 03/01/2024
 */
@Slf4j
@RequiredArgsConstructor
public class LogFilter extends OncePerRequestFilter {

    private final LogMapper logMapper;

    private final List<String> EXCLUDE_URL_PATTERN = List.of(
             "/api/v1/swagger-ui.html"
            , "/api/v1/webjars"
            , "/api/v1/v2/api-docs"
            , "/api/v1/v3/api-docs"
            , "/api/v1/swagger-resources"
    );
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        return EXCLUDE_URL_PATTERN.stream().anyMatch(path::startsWith);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        filterChain.doFilter(wrappedRequest, response);
        Map<String, Object> logs = new HashMap<>();
        String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        String ipAddr = getIpAddr(request);
        if (ipAddr.startsWith("10.11"))
        try{
            String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
            logs.put("uri", request.getRequestURI());
            logs.put("method", request.getMethod());
            logs.put("ipAddr", ipAddr);
            logs.put("memberId", memberId);
            logs.put("body", body);
            try {
                logMapper.insertLog(logs);
            } catch (Exception e) {
            }
        }  catch (Exception e){
            logs.put("uri", request.getRequestURI());
            logs.put("method", request.getMethod());
            logs.put("ipAddr", ipAddr);
            logs.put("memberId", "");
            logs.put("body", body);
            logMapper.insertLog(logs);
        }
    }

    public static String getIpAddr(HttpServletRequest request){

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
