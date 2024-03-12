package com.hyundai.domain.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * author : 이소민
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product", type = "_doc")
public class Product {
    @Id
    private Long productId;

    private String productName;
    private String productSubName;
    private int price;
    private String productType;
    private int stock;
    private int discountRate;
    private int amount;
    private int calorie;
    private String storage;
    private String productDetail;
    private String thumbnailImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}