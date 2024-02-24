package com.hyundai.global.mapper;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.manager.dto.ManagerProductDTO;

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
}
