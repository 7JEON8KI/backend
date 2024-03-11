package com.hyundai.domain.utils.dto;

import lombok.*;

/**
 * @author : 강은구
 * @fileName : OrderListDeliveryDTO
 * @description :
 * @since : 03/11/2024
 */
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderListDeliveryDTO {
    private Long orderProductCount;
    private String productName;
    private String productType;
    private String address;
    private String zipCode;
    private String receiverName;
    private String phoneNumber;
    private String orderRequired;
    private String deliveryStatus;
}