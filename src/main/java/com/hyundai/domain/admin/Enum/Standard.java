package com.hyundai.domain.admin.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Standard {
    ID("member_id"),
    NAME("member_name"),
    BIRTH("member_birth"),
    CREATED("created_at");

    private final String standard;

}
