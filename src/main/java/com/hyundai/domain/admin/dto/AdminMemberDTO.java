package com.hyundai.domain.admin.dto;

import com.hyundai.domain.login.entity.enumtype.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminMemberDTO {

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
    private LocalDateTime modifiedDate;
    private LocalDateTime deletedDate;
}