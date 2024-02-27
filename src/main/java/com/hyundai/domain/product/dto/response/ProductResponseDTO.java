package com.hyundai.domain.product.dto.response;

import com.hyundai.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long productId;
    private String productName;
    private String productSubName;
    private int price;
    private String productType;
    private int stock;
    private int discountRate;
    private String productDetail;
    private int amount;
    private int calorie;
    private String storage;
    private String thumbnailImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int isLike;
    private String themeName;
    private int rn;
    public ProductResponseDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productSubName = product.getProductSubName();
        this.price = product.getPrice();
        this.productType = product.getProductType();
        this.stock = product.getStock();
        this.discountRate = product.getDiscountRate();
        this.amount = product.getAmount();
        this.calorie = product.getCalorie();
        this.storage = product.getStorage();
        this.productDetail = product.getProductDetail();
        this.thumbnailImageUrl = product.getThumbnailImageUrl();
        this.createdAt = product.getCreatedAt();
        this.modifiedAt = product.getModifiedAt();
    }

    public static List<ProductResponseDTO> listOf(List<Product> products) {
        return products.stream().map(ProductResponseDTO::new).collect(Collectors.toList());
    }
}
