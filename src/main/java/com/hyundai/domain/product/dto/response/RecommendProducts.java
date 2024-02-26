package com.hyundai.domain.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendProducts {
    // todo : product_id -> productId로 변경
    //  product_name -> productName으로 변경
    private String product_id;
    private String product_name;
    private String price;
    private String productType;
    private int discountRate;
    private String mainImgUrl;
}
