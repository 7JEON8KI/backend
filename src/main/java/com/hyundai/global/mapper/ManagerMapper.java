package com.hyundai.global.mapper;

import com.hyundai.domain.manager.dto.ManagerProductDTO;
import com.hyundai.domain.manager.dto.ManagerTop5ProductDTO;

import java.util.List;
import java.util.Map;

public interface ManagerMapper {
    void insertProduct(ManagerProductDTO productDTO);

    void getProductByMemberId(Map<String,Object> params);

    void deleteProduct(Map<String,Object> params);
    void addProductIngredients(Map<String,Object> params);
    void addProductTheme(Map<String,Object> params);

    void deleteProductIngTheme(Map<String,Object> params);

    void getOrdersByMemberId(Map<String,Object> params);

    ManagerProductDTO getProductDetail(long productId);

    void modifyProduct(ManagerProductDTO productDTO);
    List<ManagerTop5ProductDTO> getTop5Product(String memberId);
}
