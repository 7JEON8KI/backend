package com.hyundai.domain.coupon.service;

import com.hyundai.domain.coupon.dto.CouponResponseDto;
import com.hyundai.global.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : 이지윤
 * @fileName : CouponServiceImpl
 * @description :
 * @since : 02/29/2024
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {
    private final CouponMapper couponMapper;
    @Override
    public List<CouponResponseDto> getUserCoupons(String memberId) {
        return couponMapper.findCouponList(memberId);
    }
}
