package com.hyundai.domain.coupon.service;

import com.hyundai.domain.coupon.dto.CouponResponseDto;
import java.util.List;

/**
 * author : 이지윤
 * fileName : CouponService
 * since : 2/29/2024
 */

public interface CouponService {
    List<CouponResponseDto> getUserCoupons(String memberId);
}
