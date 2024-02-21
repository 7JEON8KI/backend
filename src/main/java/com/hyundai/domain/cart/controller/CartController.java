package com.hyundai.domain.cart.controller;

import com.hyundai.domain.cart.dto.request.CartProductRequestDto;
import com.hyundai.domain.cart.service.CartService;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/**
 * author : 변형준
 * fileName : CartController
 * since : 2/14/24
 */
@Slf4j
@RestController
@RequestMapping(value = "/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 멤버가 가진 전체 장바구니 조회
    @GetMapping
    public ResponseEntity<?> getCart() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("장바구니 조회 ID:" + memberId);
        return ResponseMessage.SuccessResponse("장바구니 조회 성공", cartService.getCart(memberId));
    }

    // 장바구니 추가, 수정, 삭제
    @PostMapping
    public ResponseEntity<?> saveOrDeleteCart(@RequestBody CartProductRequestDto cartProductRequestDto) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("장바구니 추가 ID:" + memberId);
        log.debug("장바구니 추가 상품 ID:" + cartProductRequestDto.getProductId());
        String result = cartService.saveOrDeleteCart(memberId, cartProductRequestDto);
        return ResponseMessage.SuccessResponse(result, "");

    }

    // 장바구니 개수 조회
    @GetMapping("/count")
    public ResponseEntity<?> getCartCount() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("장바구니 개수 조회 ID:" + memberId);
        return ResponseMessage.SuccessResponse("장바구니 개수 조회 성공", cartService.getCart(memberId).getCartProducts().size());
    }

}
