package com.hyundai.domain.login.dto;

import com.hyundai.domain.login.entity.enumtype.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * fileName      : MemberInfoResponseDto
 * author        : 변형준
 * since         : 2/28/24
 * 내용           :
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoRequestDto {
    private String memberName;
    private String memberNickname;
    private String memberPhone;
    private String memberGender;
    private String memberBirth;
    private String infoAddr;
    private String infoZipcode;
}
