package com.hyundai.domain.product.entity;

import com.hyundai.domain.product.dto.request.ProductRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;
    private String productName;
    private String productSubName;
    private int price;
    private String productType;
    private int stock;
    private int discountRate;
    private int amount;
    private int calorie;
    private String productStorage;
    private String productDetail;
    private String thumbnailImageUrl;
    private String storeName;
    private String storeTel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(ProductRequestDTO productRequestDTO) {
        this.productName = productRequestDTO.getProductName();
        this.productSubName = productRequestDTO.getProductSubName();
        this.price = productRequestDTO.getPrice();
        this.productType = productRequestDTO.getProductType();
        this.stock = productRequestDTO.getStock();
        this.discountRate = productRequestDTO.getDiscountRate();
        this.amount = productRequestDTO.getAmount();
        this.calorie = productRequestDTO.getCalorie();
        this.productStorage = productRequestDTO.getProductStorage();
        this.productDetail = productRequestDTO.getProductDetail();
        this.thumbnailImageUrl = productRequestDTO.getThumbnailImageUrl();
        this.storeName = productRequestDTO.getStoreName();
        this.storeTel = productRequestDTO.getStoreTel();
    }
}

