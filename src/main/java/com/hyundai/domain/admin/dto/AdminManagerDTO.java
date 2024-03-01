package com.hyundai.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author : 강은구
 * @fileName : AdminManagerDTO
 * @description :
 * @since : 03/01/2024
 */

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminManagerDTO {
    private String storeId;
    private String storeName;
    private String storeTel;
    private String memberId;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String infoAddr;
    private String infoZipcode;
    private LocalDateTime memberCreatedDate;
    private LocalDateTime storeApprovedDate;
}
