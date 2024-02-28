package com.hyundai.domain.orderProduct.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderProduct {
    private Long orderProductId;
    private Long orderId;
    private Long productId;
    private int orderProductPrice;
    private int orderProductCount;
    private int orderProductDiscount;
    private String productImage;


    @Builder
    public OrderProduct(Long orderId, Long productId, int orderProductPrice, int orderProductCount, int orderProductDiscount, String productImage) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderProductPrice = orderProductPrice;
        this.orderProductCount = orderProductCount;
        this.orderProductDiscount = orderProductDiscount;
        this.productImage = productImage;
    }
}
