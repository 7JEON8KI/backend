package com.hyundai.domain.admin.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * fileName      : AdminTotalSalesDTO
 * author        : 변형준
 * since         : 3/6/24
 * 내용           :
 */

@Getter
@RequiredArgsConstructor
public class AdminTotalSalesDTO {
    private String dayOfWeek;
    private Long totalQuantity;
    private Long totalSales;
}
