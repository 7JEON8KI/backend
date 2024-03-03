package com.hyundai.domain.product.service;

import com.hyundai.domain.product.dto.request.ProductCriteria;
import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.product.dto.response.ProductWithCountResponseDTO;

import java.util.List;

public interface ProductService {
    ProductWithCountResponseDTO getProducts(ProductCriteria productCriteria, String memberId);
    ProductResponseDTO getProductDetail(Long productId);

    List<ProductResponseDTO> getSearchProducts(SearchRequestDTO searchDTO, String memberId);

    ProductWithCountResponseDTO getWineProducts(ProductCriteria productCriteria, String memberId);

    ProductWithCountResponseDTO getThemeProducts(ProductCriteria productCriteria, String memberId);
}
