package com.hyundai.domain.orders.controller;

import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.orders.dto.OrderInfo;
import com.hyundai.domain.orders.dto.OrderResponseDto;
import com.hyundai.domain.orders.service.OrderService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author : 이소민
 */
@Slf4j
@RequestMapping("/order")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    // 주문 상품 여러개를 주문하는 경우
    // xxx 예시: http://localhost:8080/member/order?products=%5B1%2C2%2C3%5D
    // xxx: %5B 는 [ 를 의미
    //      %2C 는 , 를 의미
    //      %5D 는 ] 를 의미
    @PostMapping()
    public ResponseEntity<?> orderProducts(@RequestBody List<Long> productIds) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        List<OrderInfo> orderReqDtos = productIds.stream()
                .map(id -> orderService.selectOrderInfosByProductId(id, memberId))
                .collect(Collectors.toList());
        return ResponseMessage.SuccessResponse("조회 성공", orderReqDtos);
    }

    // 주문 상품 하나만 주문하는 경우 (장바구니 없이 바로 주문하는 경우)
    @GetMapping("/one/{productId}/{productCount}")
    public ResponseEntity<?> orderOneProduct(@PathVariable Long productId, @PathVariable int productCount) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        List<OrderInfo> orderReqDtos = new ArrayList<>();
        OrderInfo orderReqDto = orderService.selectOneOrderInfoByProductId(productId, productCount, memberId);
        orderReqDtos.add(orderReqDto);
        return ResponseMessage.SuccessResponse("조회 성공", orderReqDtos);
    }

    // 주문 내역 조회
    @GetMapping("/history")
    public ResponseEntity<?> orderHistory() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrdersByMemberId(memberId);
        return ResponseMessage.SuccessResponse("조회 성공", orderResponseDtoList);
    }

    @GetMapping("/history/{orderId}")
    public ResponseEntity<?> orderHistoryDetail(@PathVariable Long orderId) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        OrderResponseDto orderResponseDto = orderService.getOrderDetailByOrderId(orderId, memberId);
        return ResponseMessage.SuccessResponse("조회 성공", orderResponseDto);
    }

}
