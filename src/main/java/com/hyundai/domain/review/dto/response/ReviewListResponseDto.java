package com.hyundai.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 변형준
 * @fileName : ReviewListResponseDto
 * @since : 2/18/24
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListResponseDto {
    List<ReviewResponseDto> reviewList;
}
