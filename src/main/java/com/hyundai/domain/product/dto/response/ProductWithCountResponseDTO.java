package com.hyundai.domain.product.dto.response;

import lombok.*;

import java.util.List;

/**
 * fileName      : ProductWithCountResponseDTO
 * author        : 변형준
 * since         : 3/3/24
 * 내용           :
 */
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithCountResponseDTO {
    private int total;
    private List<ProductResponseDTO> productResponseDTOList;
}
