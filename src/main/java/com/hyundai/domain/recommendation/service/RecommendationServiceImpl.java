package com.hyundai.domain.recommendation.service;

import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.global.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getRecommendWines(Long productId) {
        // todo productid 검증
        return productMapper.findRecommendWines(productId);
    }
}
