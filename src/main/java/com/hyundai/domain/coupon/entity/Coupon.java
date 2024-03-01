package com.hyundai.domain.coupon.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

/**
 * @author : 이지윤
 * @fileName : Coupon
 * @description :
 * @since : 02/29/2024
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    private int couponId;
    private String memberId;
    private String couponName;
    private int discountRate;
    private int discountPrice;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

}
