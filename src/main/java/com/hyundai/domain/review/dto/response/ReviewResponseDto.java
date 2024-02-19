package com.hyundai.domain.review.dto.response;

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
public class ReviewResponseDto {
    private int reviewId;
    private int productId;
    private String memberNickname;
    private String reviewTitle;
    private String reviewContent;
    private String reviewImageUrl;
    private String reviewStar;
    private String modifiedAt;
}
