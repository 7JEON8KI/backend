package com.hyundai.domain.review.entity;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

/**
 * @author : 변형준
 * @fileName : Review
 * @since : 2/18/24
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    private int reviewId;
    private String memberId;
    private int productId;
    private String reviewTitle;
    private String reviewContent;
    private String reviewImageUrl;
    private String reviewStar;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}
