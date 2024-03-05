package com.hyundai.global.mapper;

import com.hyundai.domain.admin.dto.AdminBannerDTO;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : UserMapper
 * @description :
 * @since : 03/05/2024
 */
public interface UserMapper {

    List<AdminBannerDTO> getBannerList();
}

