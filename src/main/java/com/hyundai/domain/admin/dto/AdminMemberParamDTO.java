package com.hyundai.domain.admin.dto;

import com.hyundai.domain.admin.Enum.Standard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author : 강은구
 * @fileName : AdminMemberParamDTO
 * @description :
 * @since : 02/13/2024
 */
@Getter
@Setter
@AllArgsConstructor
public class AdminMemberParamDTO {
    private Long pageNum;
    private Long amount;
    private String standard;

    public AdminMemberParamDTO(){
        this(1L,10L, Standard.ID.getStandard());
    }

}
