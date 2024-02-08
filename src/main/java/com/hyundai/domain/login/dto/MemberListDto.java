package com.hyundai.domain.login.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberListDto {
    List<MemberDto> memberList;
}
