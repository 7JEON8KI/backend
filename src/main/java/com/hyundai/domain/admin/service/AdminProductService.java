package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminProductDTO;
import com.hyundai.domain.admin.dto.AdminProductParamDTO;

import java.util.List;
import java.util.Map;

/**
 * @author : 강은구
 * @fileName : AdminProductService
 * @description :
 * @since : 02/19/2024
 */
public interface AdminProductService {
    List<AdminProductDTO> getProductByPage(AdminProductParamDTO paramDTO);

    AdminProductDTO getProductDetail(Long productId);

    void deleteProduct(Long productId);

    List<AdminProductDTO> test(Map<String, Object> test);
}
