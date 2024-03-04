package com.hyundai.domain.review.service;

import com.hyundai.domain.review.dto.request.ReviewRequestDto;
import com.hyundai.domain.review.dto.response.AbleReviewProductResponseDto;
import com.hyundai.domain.review.dto.response.MyReviewResponseDto;
import com.hyundai.domain.review.dto.response.ReviewResponseDto;

import java.util.List;

/**
 * @author : 변형준
 * @fileName : ReviewService
 * @since : 2/18/24
 */
public interface ReviewService {
    void saveReview(ReviewRequestDto reviewRequestDto, String memberId);
    void updateReview(ReviewRequestDto reviewRequestDto, String memberId);
    void deleteReview(ReviewRequestDto reviewRequestDto, String memberId);
    ReviewResponseDto getProductReviewByMemberId(String memberId, int productId);
    List<MyReviewResponseDto> getReviewsByMemberId(String memberId);
//    List<ReviewResponseDto> getProductReviews(int productId);
    List<ReviewResponseDto> getProductReviews(int productId, int pageNum);
    List<AbleReviewProductResponseDto> getAbleReviewProduct(String memberId);
}
