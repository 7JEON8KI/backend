package com.hyundai.domain.recommendation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.recommendation.dto.response.MainRecommendProducts;

import java.util.List;

/**
 * author : 이소민
 */
public interface RecommendationService {
    List<ProductResponseDTO> getRecommendWines(Long productId);

    List<MainRecommendProducts> getMainRecommendProducts(String memberId) throws JsonProcessingException;

    List<MainRecommendProducts> getGuestMainRecommendProducts();
}
