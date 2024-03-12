package com.hyundai.domain.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author : 이소민
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendProducts {
    private String productId;
    private String productName;
    private int price;
    private String productType;
    private int discountRate;
    private String mainImgUrl;
}
