package com.hyundai.domain.login.dto;

import lombok.*;

/**
 * author : 변형준
 * fileName : ShopRequestDto
 * since : 2/19/24
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
    private String storeName;
    private String storeTel;
}
