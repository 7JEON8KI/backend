package com.hyundai.domain.admin.dto;

import com.hyundai.domain.login.entity.Member;
import com.hyundai.domain.login.entity.enumtype.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Setter
@AllArgsConstructor
public class AdminMember {

    private String memberId;
    private String memberEmail;
    private String memberName;
    private String memberNickname;
    private String memberPhone;
    private String memberImage;
    private int memberGender;
    private LocalDate memberBirth;
    private Role memberRole;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;
}