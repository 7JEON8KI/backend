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
}
