package com.hyundai.domain.cart.entity;

import lombok.*;

/**
 * @author : 변형준
 * @fileName : Cart
 * @since : 2/12/24
 */

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    private int cartId;
    private int productId;
    private int memberId;

    public Cart toDto() {
        return Cart.builder()
                .cartId(cartId)
                .productId(productId)
                .memberId(memberId)
                .build();
    }

}
