package com.hyundai.domain.recommendation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.product.dto.request.ProductRequestDTO;
import com.hyundai.domain.product.dto.response.RecommendProducts;
import com.hyundai.domain.recommendation.service.RecommendationService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;
    @PostMapping()
    public ResponseEntity<?> getHybridRecommendations(@RequestBody ProductRequestDTO item) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String fastApiUrl = "http://3.37.206.197:8000/ai/hybrid-recommendations";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductRequestDTO> entity = new HttpEntity<>(item, headers);
        ResponseEntity<String> response = restTemplate.exchange(fastApiUrl, HttpMethod.POST, entity, String.class);

        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 문자열을 Java 객체로 변환
        List<RecommendProducts> products = objectMapper.readValue(response.getBody(), new TypeReference<List<RecommendProducts>>(){});

        return ResponseMessage.SuccessResponse("추천 상품 조회 성공", products);
    }

    @PostMapping("/main")
    public ResponseEntity<?> getMainRecommendations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = null;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
            memberId = userDetails.getMemberId();
        }
        return ResponseMessage.SuccessResponse("메인페이지 추천 성공", recommendationService.getRecommendProducts(memberId));
    }

    @PostMapping("/wine")
    public ResponseEntity<?> getWineRecommendations(@RequestBody ProductRequestDTO item) {
        return ResponseMessage.SuccessResponse("추천 상품 조회 성공", recommendationService.getRecommendWines(item.getProductId()));
    }
}


