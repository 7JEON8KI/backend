package com.hyundai.domain.orders.dto;

import lombok.Getter;

import java.util.List;

/**
 * fileName      : OrderResponseDto
 * author        : 변형준
 * since         : 3/2/24
 * 내용           :
 */
@Getter
public class OrderResponseDto {
    private Long orderId;
    private String orderDate;
    private String address;
    private String zipcode;
    private String receiverName;
    private String phoneNumber;
    private String orderStatus;
    private int orderNumber;
    private String orderRequired;
    private String paymentMethod;
    private List<OrderProductDto> orderProductDtoList;

}
