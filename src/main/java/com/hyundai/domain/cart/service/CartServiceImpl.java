package com.hyundai.domain.cart.service;

import com.hyundai.domain.cart.dto.request.CartProductRequestDto;
import com.hyundai.domain.cart.dto.response.CartListResponseDto;
import com.hyundai.global.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 변형준
 * @fileName : CartServiceImpl
 * @since : 2/14/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartMapper cartMapper;

    @Override
    public CartListResponseDto getCart(String memberId) {
        return cartMapper.getCart(memberId);
    }

    @Override
    public String saveOrDeleteCart(String memberId, CartProductRequestDto cartProductRequestDto) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("productId", cartProductRequestDto.getProductId());
        params.put("cartProductCnt", cartProductRequestDto.getCartProductCnt());
        // OUT 파라미터를 위한 초기화
        params.put("result", null);

        // 프로시저 호출
        cartMapper.saveOrDeleteCart(params);

        // 프로시저 실행 결과 가져오기
        Integer result = (Integer) params.get("result");

        // 결과에 따른 메시지 반환
        if (result == 1) {
            return "장바구니에 상품 추가 성공";
        } else if (result == 2) {
            return "장바구니 상품 수량 업데이트 성공";
        } else if (result == 0) {
            return "장바구니 상품 삭제 성공";
        } else {
            return "알 수 없는 오류 발생";
        }
    }

}
