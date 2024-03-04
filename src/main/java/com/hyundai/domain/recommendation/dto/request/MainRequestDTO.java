package com.hyundai.domain.recommendation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MainRequestDTO {
    private int ageGroup;
    private int gender;

    public MainRequestDTO(int ageGroup, int gender) {
        this.ageGroup = ageGroup;
        this.gender = gender;
    }
}
