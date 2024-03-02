package com.hyundai.global.mapper;

import com.hyundai.domain.orders.dto.OrderInfo;
import com.hyundai.domain.orders.dto.OrderResponseDto;
import com.hyundai.domain.orders.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    void insertOrder(Orders orders);

    OrderInfo selectOneOrderInfoByProductId(@Param("productId") Long productId, @Param("productCount") int productCount, @Param("memberId") String memberId);

    OrderInfo selectOrderInfosByProductId(@Param("productId") Long productId, @Param("memberId") String memberId);

    List<OrderResponseDto> getOrdersByMemberId(String memberId);
}
