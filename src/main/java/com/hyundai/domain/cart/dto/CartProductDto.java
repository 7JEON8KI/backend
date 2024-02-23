package com.hyundai.domain.cart.dto;

import lombok.*;

/**
 * @author : 변형준
 * @fileName : CartProductDto
 * @since : 2/12/24
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProductDto {
    private int cartProductId;
    private int productId;
    private int cartProductCnt;
    private String productName;
    private String productSubName;
    private int price;
    private String productType;
    private int stock;
    private int discountRate;
    private String productDetail;
    private int amount;
    private int calorie;
    private String storage;
    private String thumbnailImageUrl;
}
