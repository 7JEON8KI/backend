package com.hyundai.global.mapper;

import com.hyundai.domain.product.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    void findById(Map<String, Object> params);
    void findAll(Map<String, Object> params);
    List<Product> findAllByProductId(@Param("productIds") List<Long> productIds);
    void findByTheme(Map<String, Object> params);
    int selectProductStock(@Param("productId") Long id);
    void updateProductStock(@Param("productId") Long id, @Param("orderCount") int orderCount);

}
