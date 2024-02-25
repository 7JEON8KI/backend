package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminBannerDTO;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.AdminBannerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : AdminBannerServiceImpl
 * @description :
 * @since : 02/24/2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminBannerServiceImpl implements AdminBannerService{

    private final AdminBannerMapper adminBannerMapper;
    @Override
    public List<AdminBannerDTO> getBannerList() {
        List<AdminBannerDTO> results = adminBannerMapper.getBannerList();
        return results;
    }

    @Override
    public String insertBanner(AdminBannerDTO paramDTO) {

        int result = adminBannerMapper.insertBanner(paramDTO);
        if(result == 1){
            return " ";
        }
        else {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public String modifyBanner(AdminBannerDTO paramDTO) {

        int result = adminBannerMapper.modifyBanner(paramDTO);

        if(result == 1){
            return " ";
        }
        else {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public String deleteBanner(AdminBannerDTO paramDTO) {
        int result = adminBannerMapper.deleteBanner(paramDTO);

        if(result == 1){
            return " ";
        }
        else {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }
}
