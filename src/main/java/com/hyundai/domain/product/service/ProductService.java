package com.hyundai.domain.product.service;

import com.hyundai.domain.product.dto.response.ProductResposneDTO;

import java.util.List;

public interface ProductService {
    List<ProductResposneDTO> getProducts(String memberId);
    ProductResposneDTO getProductDetail(Long productId, String memberId);
}
