package com.hyundai.login.dto;


import com.hyundai.login.domain.Member;
import com.hyundai.login.domain.enumtype.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String memberId;
    private String memberEmail;
    private String memberName;
    private String memberNickname;
    private String memberPhone;
    private String memberImage;
    private int memberGender;
    private LocalDate memberBirth;
    private Role memberRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String refreshToken;

    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .memberEmail(memberEmail)
                .memberName(memberName)
                .memberNickname(memberNickname)
                .memberPhone(memberPhone)
                .memberImage(memberImage)
                .memberGender(memberGender)
                .memberBirth(memberBirth)
                .memberRole(memberRole)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .refreshToken(refreshToken)
                .build();
    }

    public Member updateToEntity() {
        return Member.builder()
                .memberId(this.getMemberId())
                .memberNickname(this.getMemberNickname())
                .memberImage(this.getMemberImage())
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    public Member updateDeletedUserToEntity() {
        return Member.builder()
                .memberId(this.getMemberId())
                .updatedAt(this.getUpdatedAt())
                .deletedAt(this.getDeletedAt())
                .build();
    }
}
