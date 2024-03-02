package com.hyundai.domain.product.service;

import com.hyundai.domain.product.dto.request.ProductCriteria;
import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getProducts(ProductCriteria productCriteria, String memberId);
    ProductResponseDTO getProductDetail(Long productId);

    List<ProductResponseDTO> getSearchProducts(SearchRequestDTO searchDTO, String memberId);

    List<ProductResponseDTO> getWineProducts(ProductCriteria productCriteria, String memberId);

    List<ProductResponseDTO> getThemeProducts(ProductCriteria productCriteria, String memberId);
}
