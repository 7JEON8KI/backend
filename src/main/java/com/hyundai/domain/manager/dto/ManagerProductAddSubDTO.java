package com.hyundai.domain.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Array;

/**
 * @author : 강은구
 * @fileName : ManagerModifyRequestDTO
 * @description :
 * @since : 02/23/2024
 */

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ManagerProductAddSubDTO {
    private Long productId;
    private Long[] ingredientIdArray;
    private Long[] themeIdArray;
}
