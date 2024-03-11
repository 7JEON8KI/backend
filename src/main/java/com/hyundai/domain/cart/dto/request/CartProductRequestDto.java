package com.hyundai.domain.cart.dto.request;

import lombok.*;

/**
 * @author : 변형준
 * @fileName : CartProductRequestDto
 * @since : 2/18/24
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProductRequestDto {
    private int productId;
    private int cartProductCnt;

    public CartProductRequestDto(int intExact) {
        this.productId = intExact;
    }
}
