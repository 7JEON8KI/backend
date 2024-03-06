package com.hyundai.global.mapper;

import com.hyundai.domain.admin.dto.AdminBannerDTO;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : AdminBannerMapper
 * @description :
 * @since : 02/24/2024
 */
public interface AdminBannerMapper {
    List<AdminBannerDTO> getBannerList();

    int insertBanner(AdminBannerDTO paramDTO);

    int modifyBanner(AdminBannerDTO paramDTO);

    int deleteBanner(long bannerId);
}
