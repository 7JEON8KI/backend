package com.hyundai.domain.manager.service;

import com.hyundai.domain.manager.dto.ManagerProductDTO;
import com.hyundai.global.mapper.ManagerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : 강은구
 * @fileName : ManagerProductServiceImpl
 * @description :
 * @since : 02/20/2024
 */

@Service
@RequiredArgsConstructor
public class ManagerProductServiceImpl implements ManagerProductService{

    private final ManagerMapper managerMapper;
    @Override
    public ManagerProductDTO insertProduct(ManagerProductDTO productDTO) {
        ManagerProductDTO result = ManagerProductDTO.builder()
                .storeId(productDTO.getStoreId())
                .productName(productDTO.getProductName())
                .productSubName(productDTO.getProductSubName())
                .price(productDTO.getPrice())
                .productType(productDTO.getProductType())
                .stock(productDTO.getStock())
                .discountRate(productDTO.getDiscountRate())
                .productDetail(productDTO.getProductDetail())
                .amount(productDTO.getAmount())
                .calorie(productDTO.getCalorie())
                .storage(productDTO.getStorage())
                .thumbnailImageUrl(productDTO.getThumbnailImageUrl())
                .build();
        return managerMapper.insertProduct(result);
    }
}
