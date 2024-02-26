package com.hyundai.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author : 강은구
 * @fileName : AdminBannerDTO
 * @description :
 * @since : 02/24/2024
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminBannerDTO {
    private Long bannerId;
    private String bannerTitle;
    private String bannerImageUrl;

    private LocalDateTime bannerStartDay;

    private LocalDateTime bannerEndDay;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}
