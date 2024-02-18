package com.hyundai.domain.cart.controller;

import com.hyundai.domain.cart.dto.request.CartProductRequestDto;
import com.hyundai.domain.cart.service.CartService;
import com.hyundai.global.message.ResponseMessage;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 변형준
 * @fileName : CartController
 * @since : 2/14/24
 */
@Log4j
@RestController
@RequestMapping(value = "/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // 멤버가 가진 전체 장바구니 조회
    @GetMapping
    public ResponseEntity getCart(HttpServletRequest request) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("장바구니 조회 ID:" + memberId);
        return ResponseMessage.SuccessResponse("장바구니 조회 성공", cartService.getCart(memberId));
    }

    // 장바구니 추가, 수정, 삭제
    @PostMapping
    public ResponseEntity saveOrDeleteCart(HttpServletRequest request, @RequestBody CartProductRequestDto cartProductRequestDto) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("장바구니 추가 ID:" + memberId);
        log.debug("장바구니 추가 상품 ID:" + cartProductRequestDto.getProductId());
        String result = cartService.saveOrDeleteCart(memberId, cartProductRequestDto);
        return ResponseMessage.SuccessResponse(result, result);

    }

    // 장바구니 개수 조회
    @GetMapping("/count")
    public ResponseEntity getCartCount(HttpServletRequest request) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("장바구니 개수 조회 ID:" + memberId);
        return ResponseMessage.SuccessResponse("장바구니 개수 조회 성공", cartService.getCart(memberId).getCartProducts().size());
    }

}
