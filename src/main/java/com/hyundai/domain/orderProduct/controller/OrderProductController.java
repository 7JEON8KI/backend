package com.hyundai.domain.orderProduct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderProductController {
    @GetMapping("/confirm/payment") // 결제 성공 페이지로 이동
    public String confirmPayment() {
        // todo : user 정보 가져오기
        return "payment/paymentSuccess";
    }
}
