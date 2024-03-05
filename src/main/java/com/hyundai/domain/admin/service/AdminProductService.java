package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminProductDTO;
import com.hyundai.domain.admin.dto.AdminProductParamDTO;
import com.hyundai.domain.admin.dto.AdminThemeDTO;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : AdminProductService
 * @description :
 * @since : 02/19/2024
 */
public interface AdminProductService {
    List<AdminProductDTO> getAllProduct();
    List<AdminProductDTO> getProductByPage(AdminProductParamDTO paramDTO);

    AdminProductDTO getProductDetail(Long productId);

    void deleteProduct(Long productId);

    void modifyProduct(AdminProductDTO paramDTO);

    void addTheme(AdminThemeDTO params);

    void deleteTheme(AdminThemeDTO params);

    void modifyTheme(AdminThemeDTO params);
}
