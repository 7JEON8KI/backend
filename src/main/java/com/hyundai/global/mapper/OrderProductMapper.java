package com.hyundai.global.mapper;

import com.hyundai.domain.orderProduct.entity.OrderProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderProductMapper {
    void insertOrderProduct(OrderProduct orderProduct);

    List<Long> selectOPIdByOrderIdAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);
}
