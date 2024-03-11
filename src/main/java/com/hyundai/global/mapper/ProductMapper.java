package com.hyundai.global.mapper;

import com.hyundai.domain.product.dto.response.IngredientDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.product.dto.response.RecommendProducts;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    void findById(Map<String, Object> params);
    void findAll(Map<String, Object> params);
//    List<Product> findAllByProductId(@Param("productIds") List<Long> productIds);
    void findWineAll(Map<String, Object> params);
    void findThemeProducts(Map<String, Object> params);

    void findProductsCount(Map<String, Object> params);
    void findWineProductsCount(Map<String, Object> params);
    void findThemeProductsCount(Map<String, Object> params);
    int selectProductStock(@Param("productId") Long id);
    void updateProductStock(@Param("productId") Long id, @Param("orderCount") int orderCount);

    List<ProductResponseDTO> findRecommendWines(Long productId);

    List<RecommendProducts> getGuestMainRecommend(List<String> productIds);

    List<IngredientDTO> getIngredients(Long productId);
}
