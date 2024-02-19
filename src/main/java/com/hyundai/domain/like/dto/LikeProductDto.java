package com.hyundai.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : 변형준
 * @fileName : LikeProductDto
 * @since : 2/15/24
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeProductDto {
    private int likeId;
    private int productId;
    private String productName;
    private String productSubName;
    private int price;
    private String producttype;
    private int stock;
    private int discountrate;
    private String productDetail;
    private int amount;
    private int calorie;
    private String storage;
    private String thumbnailimageurl;
}
