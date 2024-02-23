package com.hyundai.domain.product.service;

import com.hyundai.domain.product.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getProducts();
    ProductResponseDTO getProductDetail(Long productId);
    List<ProductResponseDTO> getThemeProducts(String themeName);
}
