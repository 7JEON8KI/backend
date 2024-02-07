package com.hyundai.login.domain;

import com.hyundai.login.domain.enumtype.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

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

    public Member toDto() {
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
}