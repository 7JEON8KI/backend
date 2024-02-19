package com.hyundai.domain.cart.service;

import com.hyundai.domain.cart.dto.request.CartProductRequestDto;
import com.hyundai.domain.cart.dto.response.CartListResponseDto;

/**
 * @author : 변형준
 * @fileName : CartService
 * @since : 2/14/24
 */
public interface CartService {
    CartListResponseDto getCart(String memberId);
    String saveOrDeleteCart(String memberId, CartProductRequestDto cartProductRequestDto);

}
