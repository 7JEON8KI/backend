package com.hyundai.domain.product.service;

import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getProducts(String memberId);
    ProductResponseDTO getProductDetail(Long productId);
    List<ProductResponseDTO> getThemeProducts(String themeName);

    // todo : productId -> productResponseDTO로 변경
    List<Long> getSearchProducts(SearchRequestDTO searchDTO, String memberId);
}
