package com.hyundai.global.mapper;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.manager.dto.ManagerProductDTO;

import java.util.List;

public interface ManagerMapper {
    ManagerProductDTO insertProduct(ManagerProductDTO productDTO);


}
