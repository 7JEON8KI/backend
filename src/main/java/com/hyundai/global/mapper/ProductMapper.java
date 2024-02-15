package com.hyundai.global.mapper;

import com.hyundai.domain.product.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface ProductMapper {
    Optional<Product> findById(Long productId);

    List<Product> findAll();

    int selectProductStock(@Param("productId") Long id);
    void updateProductStock(@Param("productId") Long id, @Param("orderCount") int orderCount);

}
