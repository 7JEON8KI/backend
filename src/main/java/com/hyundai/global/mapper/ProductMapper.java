package com.hyundai.global.mapper;

import com.hyundai.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductMapper {
    Optional<Product> findById(Long productId);

    List<Product> findAll();
}
