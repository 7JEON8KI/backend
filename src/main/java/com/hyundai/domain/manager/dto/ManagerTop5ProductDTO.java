package com.hyundai.domain.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : 강은구
 * @fileName : ManagerTop5ProductDTO
 * @description :
 * @since : 03/06/2024
 */
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ManagerTop5ProductDTO {
    private Long productId;
    private Long sum;
    private String productName;
    private String productSubName;
    private Long price;
    private Long stock;
    private String thumbnailImageUrl;

}
