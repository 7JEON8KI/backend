package com.hyundai.domain.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : 강은구
 * @fileName : ManagerOrderProductDTO
 * @description :
 * @since : 02/23/2024
 */

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ManagerOrderProductDTO {
    private Long orderDetailId;
    private Long orderId;
    private Long productId;
    private Long orderProductCnt;
    private Long orderProductPrice;
    private Long orderProductDiscount;

    private String memberId;
    private LocalDateTime orderDate;
    private String address;
    private String zipCode;
    private String receiverName;
    private String phoneNumber;
    private String orderStatus;
    private String orderNumber;
    private String orderRequired;
    private String paymentMethod;
}
