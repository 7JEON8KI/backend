package com.hyundai.domain.coupon.controller;

import com.hyundai.domain.coupon.service.CouponService;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author : 이지윤
 * fileName : CouponController
 * since : 2/29/2024
 */
@Slf4j
@RestController
@RequestMapping(value="/coupons")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping("")
    public ResponseEntity<?> getUserCoupons() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        return ResponseMessage.SuccessResponse("회원 쿠폰 조회 성공", couponService.getUserCoupons(memberId));
    }




}
