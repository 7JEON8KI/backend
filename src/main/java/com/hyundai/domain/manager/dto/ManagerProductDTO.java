package com.hyundai.domain.manager.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author : 강은구
 * @fileName : ManagerProductDTO
 * @description :
 * @since : 02/20/2024
 */

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ManagerProductDTO {
    private Long productId;
    private Long storeId;
    private String memberId;
    private String productName;
    private String productSubName;
    private Long price;
    private String productType;
    private Long stock;
    private Long discountRate;
    private String productDetail;
    private Long amount;
    private Long calorie;
    private String storage;
    private String thumbnailImageUrl;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime deletedDate;
}
