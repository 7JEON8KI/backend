package com.hyundai.domain.cart.entity;

import lombok.*;

/**
 * @author : 변형준
 * @fileName : CartProduct
 * @since : 2/12/24
 */

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProduct {
    private int cartProductId;
    private int cartId;
    private int productId;
    private int cartProductCnt;

public CartProduct toDto() {
        return CartProduct.builder()
                .cartProductId(cartProductId)
                .cartId(cartId)
                .productId(productId)
                .cartProductCnt(cartProductCnt)
                .build();
    }
}
