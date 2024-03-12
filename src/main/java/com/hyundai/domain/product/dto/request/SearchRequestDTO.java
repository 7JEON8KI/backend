package com.hyundai.domain.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * author : 이소민
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchRequestDTO {
    private List<String> preferredIngredients;
    private List<String> unwantedIngredients;
    private String keyword;
}
