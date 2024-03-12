package com.hyundai.domain.orders.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * author : 이소민
 */
@Getter
@Setter
@NoArgsConstructor
public class OrdersRequestDTO {
    List<OrderSaveDTO> orders;
}
