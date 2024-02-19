package com.hyundai.global.mapper;

import com.hyundai.domain.review.dto.response.ReviewResponseDto;

import java.util.List;
import java.util.Map;

/**
 * @author : 변형준
 * @fileName : ReviewMapper
 * @since : 2/18/24
 */
public interface ReviewMapper {
    void saveReview(Map<String, Object> params);
    void updateReview(Map<String, Object> params);
    void deleteReview(Map<String, Object> params);
    ReviewResponseDto getProductReviewByMemberId(Map<String, Object> params);
    List<ReviewResponseDto> getReviewsByMemberId(Map<String, Object> params);
    List<ReviewResponseDto> getProductReviews(Map<String, Object> params);
}
