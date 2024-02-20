package com.hyundai.domain.admin.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : 강은구
 * @fileName : AdminProductDTO
 * @description :
 * @since : 02/19/2024
 */
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class AdminProductDTO {
    private Long productId;
    private Long storeId;
    private String productName;
    private String productSubName;
    private Long price;
    private String productType;
    private Long stock;
    private Long discountRate;
    private String productDetail;
    private Long amount;
    private Long calorie;
    private String storage;
    private String thumbnailImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<AdminThemeDTO> themeList;
    private List<AdminProductIngredientDTO> ingredientList;
}
