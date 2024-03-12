package com.hyundai.domain.recommendation.dto.response;

import com.hyundai.domain.product.dto.response.RecommendProducts;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MainRecommendProducts {
    private String ment;
    private List<RecommendProducts> products;
}
