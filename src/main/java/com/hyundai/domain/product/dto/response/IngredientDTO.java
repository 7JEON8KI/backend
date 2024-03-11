package com.hyundai.domain.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * fileName      : IngredientDTO
 * author        : 변형준
 * since         : 3/11/24
 * 내용           :
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
   private int ingredientId;
   private String ingredientName;
}
