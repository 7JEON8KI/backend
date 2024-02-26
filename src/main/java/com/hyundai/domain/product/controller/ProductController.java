package com.hyundai.domain.product.controller;



//import com.hyundai.domain.product.ProductMigrationFromOracleToElasticsearch;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.product.service.ProductService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
//    private final ProductMigrationFromOracleToElasticsearch migration;

    // 상품 리스트 조회
    /*@GetMapping()
    public ResponseEntity<?> getProducts() {
        return ResponseMessage.SuccessResponse("상품 리스트 조회 성공", productService.getProducts());
    }*/

    /*@PostMapping("/migrate")
    public ResponseEntity<?> migrate() {
        migration.migrate();
        return ResponseMessage.SuccessResponse("migrate success", "");
    }*/

    // 상품 리스트 조회
    @GetMapping()
    public ResponseEntity<?> getProducts(@RequestBody(required = false) SearchRequestDTO searchDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = null;
        log.debug("authentication : " + authentication);
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
            memberId = userDetails.getMemberId();
            log.debug("memberId : " + memberId);
        }

        if (searchDTO != null) {
            // todo : productId -> productResponseDTO로 변경
            List<Long> products = productService.getSearchProducts(searchDTO, memberId);
            return ResponseMessage.SuccessResponse("상품 검색 성공", products);
        } else {
            List<ProductResponseDTO> products = productService.getProducts(memberId);
            return ResponseMessage.SuccessResponse("상품 리스트 조회 성공", products);
        }
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
