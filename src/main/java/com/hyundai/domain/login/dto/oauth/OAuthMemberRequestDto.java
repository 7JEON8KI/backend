package com.hyundai.domain.login.dto.oauth;

import lombok.*;

/**
 * @author : 변형준
 * @fileName : OAuthMemberRequestDto
 * @since : 2/13/24
 */

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OAuthMemberRequestDto {
    private String memberEmail;
    private String memberName;
    private String memberPhone;
    private String memberNickname;
    private String memberImage;
    private String memberGender;
    private String memberBirth;
    private String infoAddr;
    private String infoZipcode;
    private String refreshToken;
}
