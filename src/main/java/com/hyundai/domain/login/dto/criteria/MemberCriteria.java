package com.hyundai.domain.login.dto.criteria;

import com.hyundai.domain.login.dto.Criteria;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
public class MemberCriteria extends Criteria {
    private String memberId;
}
