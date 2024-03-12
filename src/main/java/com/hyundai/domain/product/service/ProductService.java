package com.hyundai.domain.product.service;

import com.hyundai.domain.product.dto.request.ProductCriteria;
import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.dto.response.IngredientDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.product.dto.response.ProductWithCountResponseDTO;
import com.hyundai.domain.product.dto.response.RecommendProducts;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * author : 이소민
 */

public interface ProductService {
    ProductWithCountResponseDTO getProducts(ProductCriteria productCriteria, String memberId);
    ProductResponseDTO getProductDetail(Long productId);
    List<ProductResponseDTO> getSearchProducts(SearchRequestDTO searchDTO, String memberId);
    ProductWithCountResponseDTO getWineProducts(ProductCriteria productCriteria, String memberId);
    ProductWithCountResponseDTO getThemeProducts(ProductCriteria productCriteria, String memberId);
    List<RecommendProducts> getImageSearchProducts(MultipartFile image) throws IOException;
    List<IngredientDTO> getIngredients(Long productId);
}
