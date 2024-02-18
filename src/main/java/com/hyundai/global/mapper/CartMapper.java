package com.hyundai.global.mapper;

import com.hyundai.domain.cart.dto.response.CartListResponseDto;

import java.util.Map;

/**
 * @author : 변형준
 * @fileName : CartMapper
 * @since : 2/12/24
 */
public interface CartMapper {
    CartListResponseDto getCart(String memberId);
    void saveOrDeleteCart(Map<String, Object> params);

}
