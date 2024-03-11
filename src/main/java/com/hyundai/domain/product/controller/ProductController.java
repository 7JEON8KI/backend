package com.hyundai.domain.product.controller;

import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.product.dto.request.ProductCriteria;
import com.hyundai.domain.product.dto.request.WrapperSearchDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.product.service.ProductService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
//    private final ProductMigrationFromOracleToElasticsearch migration;


    /*@PostMapping("/migrate")
    public ResponseEntity<?> migrate() {
        migration.migrate();
        return ResponseMessage.SuccessResponse("migrate success", "");
    }*/

    // 상품 리스트 조회
    @PostMapping()
    public ResponseEntity<?> getProducts(@RequestBody WrapperSearchDTO wrapperSearchDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = null;
        log.debug("authentication : " + authentication);
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
            memberId = userDetails.getMemberId();
            log.debug("memberId : " + memberId);
        }

        if (wrapperSearchDTO.getSearchRequestDTO() != null) {
            List<ProductResponseDTO> products = productService.getSearchProducts(wrapperSearchDTO.getSearchRequestDTO(), memberId);
            return ResponseMessage.SuccessResponse("상품 검색 성공", products);
        } else {
            return ResponseMessage.SuccessResponse("상품 리스트 조회 성공", productService.getProducts(wrapperSearchDTO.getProductCriteria(), memberId));
        }
    }

    // 상품 이미지 검색
    @PostMapping("/image-search")
    public ResponseEntity<?> getImageSearchProducts(MultipartFile image) throws IOException {
        return ResponseMessage.SuccessResponse("상품 이미지 검색 성공", productService.getImageSearchProducts(image));
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long productId) {
        return ResponseMessage.SuccessResponse("상품 상세 조회 성공", productService.getProductDetail(productId));
    }

    // 와인 상품 조회
    @PostMapping("/wine")
    public ResponseEntity<?> getWineProducts(@RequestBody ProductCriteria productCriteria) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = null;
        log.debug("authentication : " + authentication);
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
            memberId = userDetails.getMemberId();
            log.debug("memberId : " + memberId);
        }

        return ResponseMessage.SuccessResponse("와인 상품 리스트 조회 성공", productService.getWineProducts(productCriteria, memberId));
    }

    @PostMapping("/theme")
    public ResponseEntity<?> getThemeProducts(@RequestBody ProductCriteria productCriteria) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = null;
        log.debug("authentication : " + authentication);
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
            memberId = userDetails.getMemberId();
            log.debug("memberId : " + memberId);
        }

        return ResponseMessage.SuccessResponse("테마 상품 리스트 조회 성공", productService.getThemeProducts(productCriteria, memberId));
    }

    @GetMapping("/ingredients/{productId}")
    public ResponseEntity<?> getIngredients(@PathVariable Long productId) {
        return ResponseMessage.SuccessResponse("재료 리스트 조회 성공", productService.getIngredients(productId));
    }
}
