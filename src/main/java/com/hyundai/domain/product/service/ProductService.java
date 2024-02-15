package com.hyundai.domain.product.service;

import com.hyundai.domain.product.dto.response.ProductResposneDTO;

import java.util.List;

public interface ProductService {
    List<ProductResposneDTO> getProducts(Long userId);
    ProductResposneDTO getProductDetail(Long productId, Long userId);
}
