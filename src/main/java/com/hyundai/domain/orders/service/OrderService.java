package com.hyundai.domain.orders.service;

import com.hyundai.domain.orders.dto.OrderInfo;
import com.hyundai.global.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;

    public OrderInfo selectOneOrderInfoByProductId(Long productId, int productCount, Long memberId) {
        return orderMapper.selectOneOrderInfoByProductId(productId, productCount, memberId);
    }

    public OrderInfo selectOrderInfosByProductId(Long productId, Long memberId) {
        return orderMapper.selectOrderInfosByProductId(productId, memberId);
    }
}
