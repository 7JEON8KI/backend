package com.hyundai.domain.review.service;

import com.hyundai.domain.review.dto.request.ReviewRequestDto;
import com.hyundai.domain.review.dto.response.AbleReviewProductResponseDto;
import com.hyundai.domain.review.dto.response.MyReviewResponseDto;
import com.hyundai.domain.review.dto.response.ReviewResponseDto;
import com.hyundai.domain.review.entity.enumType.ReviewGetType;
import com.hyundai.domain.review.entity.enumType.ReviewOperatation;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 변형준
 * @fileName : ReviewServiceImpl
 * @since : 2/18/24
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewMapper reviewMapper;
    @Override
    public void saveReview(ReviewRequestDto reviewRequestDto, String memberId) {
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
        if (result == null || result != 1) {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public void updateReview(ReviewRequestDto reviewRequestDto, String memberId) {
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
        if (result == null || result != 2) {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public void deleteReview(ReviewRequestDto reviewRequestDto, String memberId) {
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
        if (result == null || result != 0) {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public ReviewResponseDto getProductReviewByMemberId(String memberId, int productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("productId", productId);
        params.put("reviewType", ReviewGetType.MY_PRODUCT_REVIEW.toString());

        return reviewMapper.getProductReviewByMemberId(params).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.REVIEW_NOT_FOUND)
        );
    }

    @Override
    public List<MyReviewResponseDto> getReviewsByMemberId(String memberId) {
        return reviewMapper.getReviewsByMemberId(memberId);
    }


    @Override
    public List<ReviewResponseDto> getProductReviews(int productId, int pageNum){
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("reviewType", ReviewGetType.PRODUCT_REVIEWS.toString());
        params.put("cursor", null);
        params.put("pageNum", pageNum);
        params.put("pageSize", 5);
        List<ReviewResponseDto> reviews = reviewMapper.getProductReview(params);
        if(reviews == null || reviews.isEmpty()) {
            throw new GlobalException(GlobalErrorCode.SUCCESS);
        }
        return reviews;
    }

    @Override
    public List<AbleReviewProductResponseDto> getAbleReviewProduct(String memberId) {
        return reviewMapper.getAbleReviewProduct(memberId);
    }
}
