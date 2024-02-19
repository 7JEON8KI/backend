package com.hyundai.domain.review.dto.request;

import com.hyundai.domain.review.entity.Review;
import lombok.*;

/**
 * @author : 변형준
 * @fileName : ReviewRequestDto
 * @since : 2/18/24
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private int productId;
    private String reviewTitle;
    private String reviewContent;
    private String reviewImageUrl;
    private String reviewStar;

}
