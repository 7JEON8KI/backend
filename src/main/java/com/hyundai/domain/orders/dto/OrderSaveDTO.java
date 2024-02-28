package com.hyundai.domain.orders.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderSaveDTO {
    private String memberId;
    private Long productId;
    private int orderPrice;
    private int orderCount;
    private int orderDiscount;
    private String receiverName;
    private String phoneNumber;
    private int zipcode;
    private String orderNumber;
    private String address;
    private String orderRequired;
    private String paymentMethod;
    private String productImage;
    private LocalDateTime deliveryDepartureTime;
}
