package com.hyundai.domain.product.controller;



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
    public ResponseEntity<?> getProducts() {
        return ResponseMessage.SuccessResponse("상품 리스트 조회 성공", productService.getProducts());
    }

    // 테마 상품 리스트 조회
    @GetMapping("/theme/{themeName}")
    public ResponseEntity<?> getThemeProducts(@PathVariable String themeName) {
        return ResponseMessage.SuccessResponse("상품 리스트 조회 성공", productService.getThemeProducts(themeName));
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long productId) {
        return ResponseMessage.SuccessResponse("상품 상세 조회 성공", productService.getProductDetail(productId));
    }
}
