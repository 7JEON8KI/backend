package com.hyundai.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
    private String selectId;
    private int pageNum;
    private int pageSize;
    private String sorted;
}