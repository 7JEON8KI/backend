package com.hyundai.global.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.Map;

public interface ProductMapper {
    void findById(Map<String, Object> params);
    void findAll(Map<String, Object> params);
    void findByTheme(Map<String, Object> params);
    int selectProductStock(@Param("productId") Long id);
    void updateProductStock(@Param("productId") Long id, @Param("orderCount") int orderCount);

}
