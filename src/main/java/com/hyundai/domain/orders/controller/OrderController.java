package com.hyundai.domain.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.domain.orders.dto.OrderInfo;
import com.hyundai.domain.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 상품 여러개를 주문하는 경우
    // xxx 예시: http://localhost:8080/member/order?products=%5B1%2C2%2C3%5D
    // xxx: %5B 는 [ 를 의미
    //      %2C 는 , 를 의미
    //      %5D 는 ] 를 의미
    @GetMapping("/member/order")
    public String orderProducts(Model model, @RequestParam String products) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> productIds = objectMapper.readValue(products, new TypeReference<List<Long>>(){});
        List<OrderInfo> orderReqDtos = productIds.stream()
                .map(id -> orderService.selectOrderInfosByProductId(id, 1001L))
                .collect(Collectors.toList());

        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "payment/payment";
    }

    // 주문 상품 하나만 주문하는 경우 (장바구니 없이 바로 주문하는 경우)
    @GetMapping("/member/order/one/{productId}/{productCount}")
    public String orderOneProduct(/*@Login SessionUser sessionUser, */Model model, @PathVariable Long productId, @PathVariable int productCount) {
        List<OrderInfo> orderReqDtos = new ArrayList<>();
        OrderInfo orderReqDto = orderService.selectOneOrderInfoByProductId(productId, productCount, 1001L);
        orderReqDtos.add(orderReqDto);
        model.addAttribute("products", orderReqDtos);
        model.addAttribute("totalSize", orderReqDtos.size());
        return "payment/payment";
    }
}
