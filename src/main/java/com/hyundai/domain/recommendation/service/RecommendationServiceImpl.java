package com.hyundai.domain.recommendation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.domain.login.dto.MemberInfoResponseDto;
import com.hyundai.domain.login.service.oauth.OAuthService;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.product.dto.response.RecommendProducts;
import com.hyundai.domain.recommendation.dto.request.MainRequestDTO;
import com.hyundai.domain.recommendation.dto.response.MainRecommendProducts;
import com.hyundai.global.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

/**
 * author : 이소민
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final ProductMapper productMapper;
    private final OAuthService oAuthService;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getRecommendWines(Long productId) {
        return productMapper.findRecommendWines(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MainRecommendProducts> getGuestMainRecommendProducts() {
        // 현재 시간 가져오기
        LocalTime currentTime = LocalTime.now();
        log.info(String.valueOf(currentTime));
        String ment;
        List<String> productIds;
        if (currentTime.isAfter(LocalTime.of(0, 0)) && currentTime.isBefore(LocalTime.of(14, 0))) {
            ment = "점심엔 이거 어때요?";
            productIds = List.of("502", "465", "438", "316", "350", "425");
        } else {
            ment = "저녁엔 이거 어때요?";
            productIds = List.of("301", "308", "573", "398", "322", "472");
        }
        log.info(ment);
        List<RecommendProducts> products = productMapper.getGuestMainRecommend(productIds);
        return List.of(new MainRecommendProducts(ment, products));
    }


    @Transactional(readOnly = true)
    public List<MainRecommendProducts> getMainRecommendProducts(String memberId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String fastApiUrl = "http://3.37.206.197:8000/ai/age-gender-recommendation";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MemberInfoResponseDto memberInfo = oAuthService.getMemberInfo(memberId);

        LocalDate birthDate = memberInfo.getMemberBirth();
        int birthYear = birthDate.getYear();

        // 나이 그룹 설정
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int age = currentYear - birthYear + 1;
        int ageGroup;

        int decade = (age / 10) * 10;
        ageGroup = (decade == 0) ? 10 : decade;

        int gender = Integer.parseInt(memberInfo.getMemberGender());

        HttpEntity<MainRequestDTO> entity = new HttpEntity<>(new MainRequestDTO(ageGroup, gender), headers);
        ResponseEntity<String> response = restTemplate.exchange(fastApiUrl, HttpMethod.POST, entity, String.class);

        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 문자열을 Java 객체로 변환
        List<MainRecommendProducts> products = objectMapper.readValue(response.getBody(), new TypeReference<List<MainRecommendProducts>>(){});
        return products;
    }
}
