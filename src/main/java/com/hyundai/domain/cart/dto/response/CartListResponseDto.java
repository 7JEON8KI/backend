package com.hyundai.domain.cart.dto.response;

import com.hyundai.domain.cart.dto.CartProductDto;
import lombok.*;

import java.util.List;

/**
 * @author : 변형준
 * @fileName : CartListResponseDto
 * @since : 2/12/24
 */

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartListResponseDto {
    private int cartId;
    private String memberId;
    private String modifiedAt;
    private List<CartProductDto> cartProducts;

}
