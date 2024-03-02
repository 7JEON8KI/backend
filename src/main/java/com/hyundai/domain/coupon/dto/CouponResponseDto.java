package com.hyundai.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

/**
 * @author : 이지윤
 * @fileName : CouponResponseDto
 * @description :
 * @since : 02/29/2024
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponseDto {
    @Id
    private int couponId;
    private String memberId;
    private String couponName;
    private int discountRate;
    private int discountPrice;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
}
