package com.hyundai.global.mapper;

import com.hyundai.domain.coupon.dto.CouponResponseDto;
import java.util.List;

/**
 * @author : 이지윤
 * @fileName : CouponMapper
 * @description :
 * @since : 02/29/2024
 */
public interface CouponMapper {
    List<CouponResponseDto> findCouponList(String memberId);
}
