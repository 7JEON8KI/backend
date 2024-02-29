package com.hyundai.domain.login.dto;

import com.hyundai.domain.login.entity.enumtype.Role;
import lombok.*;

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
public class MemberInfoResponseDto {
    private String memberId;
    private String memberEmail;
    private String memberName;
    private String memberNickname;
    private String memberPhone;
    private String memberGender;
    private LocalDate memberBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String infoAddr;
    private String infoZipcode;
}
