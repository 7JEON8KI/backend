package com.hyundai.domain.recommendation.service;

import com.hyundai.domain.product.dto.response.ProductResponseDTO;

import java.util.List;

public interface RecommendationService {
    List<ProductResponseDTO> getRecommendWines(Long productId);
}
