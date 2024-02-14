package com.hyundai.domain.product.controller;


import com.hyundai.domain.product.dto.request.ProductRequestDTO;
import com.hyundai.domain.product.service.ProductService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 리스트 조회
    @GetMapping()
    public ResponseEntity getProducts() {
        Long userId = 1L;
        return ResponseMessage.SuccessResponse("상품 리스트 조회 성공", productService.getProducts(userId));
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity getProductDetail(@PathVariable Long productId) {
        Long userId = 1L;
        return ResponseMessage.SuccessResponse("상품 상세 조회 성공", productService.getProductDetail(productId, userId));
    }
}
