package com.hyundai.domain.user.service;

import com.hyundai.domain.admin.dto.AdminBannerDTO;
import com.hyundai.global.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : UserService
 * @description :
 * @since : 03/03/2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper usermapper;

    public List<AdminBannerDTO> getBannerList() {
        List<AdminBannerDTO> results = usermapper.getBannerList();
        return results;
    }
}
