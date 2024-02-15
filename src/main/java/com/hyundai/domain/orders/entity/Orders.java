package com.hyundai.domain.orders.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Orders {
    private Long orderId;
    private String memberId;
    private String orderNumber;
    private String receiverName;
    private String phoneNumber;
    private int zipcode;
    private String address;
    private String orderRequired;
    private String orderStatus;
    private String paymentMethod;
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
