package com.hyundai.domain.orders.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author : 이소민
 */

@Getter
@Setter
@NoArgsConstructor
public class OrderSaveDTO {
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
}
