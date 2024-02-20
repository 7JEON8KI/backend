package com.hyundai.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author : 강은구
 * @fileName : AdminProductParamDTO
 * @description :
 * @since : 02/19/2024
 */
@Getter
@Builder
@AllArgsConstructor
public class AdminProductParamDTO {
    private Long pageNum;
    private Long pageAmount;

    private String standard;
    private String sort;
    public AdminProductParamDTO(){
        this(1L,10L, "price", "desc");
    }
}
