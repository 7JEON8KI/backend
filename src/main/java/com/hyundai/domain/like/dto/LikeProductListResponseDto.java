package com.hyundai.domain.like.dto;

import lombok.*;

import java.util.List;

/**
 * @author : 변형준
 * @fileName : LikeProductListResponseDto
 * @since : 2/15/24
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeProductListResponseDto {
    List<LikeProductDto> likeProductList;
}
