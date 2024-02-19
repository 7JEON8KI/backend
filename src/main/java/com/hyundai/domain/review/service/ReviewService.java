package com.hyundai.domain.review.service;

import com.hyundai.domain.review.dto.request.ReviewRequestDto;
import com.hyundai.domain.review.dto.response.ReviewListResponseDto;
import com.hyundai.domain.review.dto.response.ReviewResponseDto;

import java.util.List;

/**
 * @author : 변형준
 * @fileName : ReviewService
 * @since : 2/18/24
 */
public interface ReviewService {
    String saveReview(ReviewRequestDto reviewRequestDto, String memberId);
    String updateReview(ReviewRequestDto reviewRequestDto, String memberId);
    String deleteReview(ReviewRequestDto reviewRequestDto, String memberId);
    ReviewResponseDto getProductReviewByMemberId(String memberId, int productId);
    List<ReviewResponseDto> getReviewsByMemberId(String memberId);
    List<ReviewResponseDto> getProductReviews(int productId);
}
