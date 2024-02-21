package com.hyundai.domain.manager.service;

import com.hyundai.domain.manager.dto.ManagerProductDTO;

/**
 * @author : 강은구
 * @fileName : ManagerProductService
 * @description :
 * @since : 02/20/2024
 */
public interface ManagerProductService {
    ManagerProductDTO insertProduct(ManagerProductDTO productDTO);
}
