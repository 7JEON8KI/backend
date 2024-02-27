package com.hyundai.domain.product.dto.request;

import lombok.Getter;

/**
 * fileName      : ProductCriteria
 * author        : 변형준
 * since         : 2/26/24
 * 내용           :
 */
@Getter
public class ProductCriteria {
    private int pageNum;
    private int pageAmount;
    private String sort;
    private int includeSoldOut;
    private String themeName;

    public ProductCriteria() {
        this.pageNum = 1;
        this.pageAmount = 12;
        this.sort = "new";
        this.includeSoldOut = 0;
        this.themeName = null;
    }
}
