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
public class ProductResposneDTO {
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

    public ProductResposneDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productSubName = product.getProductSubName();
        this.price = product.getPrice();
        this.productType = product.getProductType();
        this.stock = product.getStock();
        this.discountRate = product.getDiscountRate();
        this.amount = product.getAmount();
        this.calorie = product.getCalorie();
        this.productStorage = product.getProductStorage();
        this.productDetail = product.getProductDetail();
        this.thumbnailImageUrl = product.getThumbnailImageUrl();
        this.storeName = product.getStoreName();
        this.storeTel = product.getStoreTel();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    public static List<ProductResposneDTO> listOf(List<Product> products) {
        return products.stream().map(ProductResposneDTO::new).collect(Collectors.toList());
    }
}
