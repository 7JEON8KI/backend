package com.hyundai.domain.orders.dto;

import lombok.Getter;

/**
 * fileName      : OrderProductDto
 * author        : 변형준
 * since         : 3/2/24
 * 내용           :
 */
@Getter
public class OrderProductDto {
    private Long orderproductId;
    private Long productId;
    private int orderProductPrice;
    private int orderProductCount;
    private int orderProductDiscount;
    private String thumbnailImageUrl;
    private String productName;
}
