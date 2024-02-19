package com.hyundai.domain.review.service;

import com.hyundai.domain.review.dto.request.ReviewRequestDto;
import com.hyundai.domain.review.dto.response.ReviewListResponseDto;
import com.hyundai.domain.review.dto.response.ReviewResponseDto;
import com.hyundai.domain.review.entity.enumType.ReviewGetType;
import com.hyundai.domain.review.entity.enumType.ReviewOperatation;
import com.hyundai.global.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 변형준
 * @fileName : ReviewServiceImpl
 * @since : 2/18/24
 */
@Log4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewMapper reviewMapper;
    @Override
    public String saveReview(ReviewRequestDto reviewRequestDto, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("productId", reviewRequestDto.getProductId());
        params.put("reviewTitle", reviewRequestDto.getReviewTitle());
        params.put("reviewContent", reviewRequestDto.getReviewContent());
        params.put("reviewImageUrl", reviewRequestDto.getReviewImageUrl());
        params.put("reviewStar", reviewRequestDto.getReviewStar());
        params.put("operation", ReviewOperatation.ADD.name());
        // OUT 파라미터를 위한 초기화
        params.put("result", null);
        reviewMapper.saveReview(params);
        // 프로시저 실행 결과 가져오기
        Integer result = (Integer) params.get("result");
        // 결과에 따른 메시지 반환
        if (result == 1) {
            return "리뷰 추가 성공";
        } else {
            return "알 수 없는 오류 발생";
        }
    }

    @Override
    public String updateReview(ReviewRequestDto reviewRequestDto, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("productId", reviewRequestDto.getProductId());
        params.put("reviewTitle", reviewRequestDto.getReviewTitle());
        params.put("reviewContent", reviewRequestDto.getReviewContent());
        params.put("reviewImageUrl", reviewRequestDto.getReviewImageUrl());
        params.put("reviewStar", reviewRequestDto.getReviewStar());
        params.put("operation", ReviewOperatation.UPDATE.name());
        // OUT 파라미터를 위한 초기화
        params.put("result", null);
        reviewMapper.updateReview(params);
        // 프로시저 실행 결과 가져오기
        Integer result = (Integer) params.get("result");
        // 결과에 따른 메시지 반환
        if (result == 2) {
            return "리뷰 업데이트 성공";
        } else {
            return "알 수 없는 오류 발생";
        }
    }

    @Override
    public String deleteReview(ReviewRequestDto reviewRequestDto, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("productId", reviewRequestDto.getProductId());
        params.put("operation", ReviewOperatation.DELETE.name());
        // OUT 파라미터를 위한 초기화
        params.put("result", null);
        reviewMapper.deleteReview(params);
        // 프로시저 실행 결과 가져오기
        Integer result = (Integer) params.get("result");
        // 결과에 따른 메시지 반환
         if (result == 0) {
            return "리뷰 삭제 성공";
        } else {
            return "알 수 없는 오류 발생";
        }

    }

    @Override
    public ReviewResponseDto getProductReviewByMemberId(String memberId, int productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("productId", productId);
        params.put("reviewType", ReviewGetType.MY_PRODUCT_REVIEW.name());
        params.put("cursor", null);
        ReviewResponseDto reviewResponseDto = reviewMapper.getProductReviewByMemberId(params);
        if (reviewResponseDto != null) {
            return reviewResponseDto;
        } else {
            return null;
        }
    }

    @Override
    public List<ReviewResponseDto> getReviewsByMemberId(String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("reviewType", ReviewGetType.MY_REVIEWS.name());
        params.put("cursor", null);
        List<ReviewResponseDto> reviewResponseDtos = reviewMapper.getReviewsByMemberId(params);
        if (reviewResponseDtos != null) {
            return reviewResponseDtos;
        } else {
            return null;
        }
    }

    @Override
    public List<ReviewResponseDto> getProductReviews(int productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("reviewType", ReviewGetType.PRODUCT_REVIEWS.name());
        params.put("cursor", null);

        List<ReviewResponseDto> reviewResponseDtos = reviewMapper.getProductReviews(params);
        if (reviewResponseDtos != null) {
            return reviewResponseDtos;
        } else {
            return null;
        }
    }


}
