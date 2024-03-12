package com.hyundai.domain.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author : 이소민
 */

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDTO {
    private Long productId;
    private String productName;
    private String productMainImage;
    /*private String productSubName;
    private int price;
    private String productType;
    private int stock;
    private int discountRate;
    private int amount;
    private int calorie;
    private String productStorage;
    private String productDetail;
    private String thumbnailImageUrl;
    private String storeName;
    private String storeTel;*/
}
