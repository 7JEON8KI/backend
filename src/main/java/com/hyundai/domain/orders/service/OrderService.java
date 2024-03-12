package com.hyundai.domain.orders.service;

import com.hyundai.domain.orders.dto.OrderInfo;
import com.hyundai.domain.orders.dto.OrderResponseDto;
import com.hyundai.global.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author : 이소민
 */

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;

    public OrderInfo selectOneOrderInfoByProductId(Long productId, int productCount, String memberId) {
        return orderMapper.selectOneOrderInfoByProductId(productId, productCount, memberId);
    }

    public OrderInfo selectOrderInfosByProductId(Long productId, String memberId) {
        return orderMapper.selectOrderInfosByProductId(productId, memberId);
    }

    public List<OrderResponseDto> getOrdersByMemberId(String memberId) {
        return orderMapper.getOrdersByMemberId(memberId);
    }

    public OrderResponseDto getOrderDetailByOrderId(Long orderId, String memberId) {
        return orderMapper.getOrderDetailByOrderId(orderId, memberId);
    }
}
