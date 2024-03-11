package com.hyundai.domain.orders.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Orders {
    private Long orderId;
    private final String memberId;
    private final String orderNumber;
    private final String receiverName;
    private final String phoneNumber;
    private final int zipcode;
    private final String address;
    private final String orderRequired;
    private final String orderStatus;
    private final String paymentMethod;
    private LocalDateTime orderDate;

    @Builder
    public Orders(String memberId, String orderNumber, String receiverName, String phoneNumber, int zipcode, String address, String orderRequired, String orderStatus, String paymentMethod) {
        this.memberId = memberId;
        this.orderNumber = orderNumber;
        this.receiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.zipcode = zipcode;
        this.address = address;
        this.orderRequired = orderRequired;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
    }
}
