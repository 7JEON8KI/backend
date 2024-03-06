package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminBannerDTO;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : AdminBannerService
 * @description :
 * @since : 02/24/2024
 */
public interface AdminBannerService {
    List<AdminBannerDTO> getBannerList();

    String insertBanner(AdminBannerDTO paramDTO);

    String modifyBanner(AdminBannerDTO paramDTO);

    String deleteBanner(long bannerId);
}
