package com.hyundai.domain.review.dto.response;

import lombok.Getter;

/**
 * fileName      : AbleReviewProductResponseDto
 * author        : 변형준
 * since         : 3/2/24
 * 내용           :
 */
@Getter
public class AbleReviewProductResponseDto {
    private Long productId;
    private String productName;
    private String thumbnailImageUrl;
    private String orderDate;
}
