package com.hyundai.domain.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * fileName      : WrapperSearchDTO
 * author        : 변형준
 * since         : 2/27/24
 * 내용           :
 */
@Getter
@NoArgsConstructor
public class WrapperSearchDTO {
    private ProductCriteria productCriteria;
    private SearchRequestDTO searchRequestDTO;
}
