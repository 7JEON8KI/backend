package com.hyundai.domain.product.service;

import com.hyundai.domain.product.entity.Product;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetProductService {
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productMapper.findById(productId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.PRODUCT_NOT_FOUND)
        );
    }
}
