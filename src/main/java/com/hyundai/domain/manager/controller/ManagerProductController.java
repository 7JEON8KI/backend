package com.hyundai.domain.manager.controller;

import com.hyundai.domain.manager.dto.ManagerProductDTO;
import com.hyundai.domain.manager.service.ManagerProductService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : 강은구
 * @fileName : ManagerProductController
 * @description :
 * @since : 02/20/2024
 */

@Slf4j
@RequestMapping("/manager")
@RestController
@RequiredArgsConstructor
public class ManagerProductController {

    private final ManagerProductService managerProductService;
    @PostMapping("/products")
    public ResponseEntity<?> insertProduct(@RequestBody ManagerProductDTO paramDTO){

        return ResponseMessage.SuccessResponse(paramDTO.getProductName() + " 상품을 등록했습니다.", managerProductService.insertProduct(paramDTO));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProductByID(){

        return ResponseMessage.SuccessResponse(" 매니저의 상품을 불러왔습니다. ", managerProductService.getProductByMemberId());
    }
    @DeleteMapping("/products")
    public ResponseEntity<?> deleteProduct(@RequestBody Map<String, Object> paramDTO){
        return ResponseMessage.SuccessResponse( paramDTO.get("productId") + "번 상품을 삭제했습니다. ", managerProductService.deleteProduct(paramDTO));
    }
    @PostMapping("/products/addIngAndTheme")
    public ResponseEntity<?> addProductIngTheme(@RequestBody Map<String,Object> paramDTO){

        return ResponseMessage.SuccessResponse(" 상품에 재료와 테마를 추가했습니다. ", managerProductService.addProductIngTheme(paramDTO));
    }
    @DeleteMapping("/products/deleteIngAndTheme")
    public ResponseEntity<?> deleteProductIngTheme(@RequestBody Map<String,Object> paramDTO){

        return ResponseMessage.SuccessResponse(" 상품에 재료 또는 테마를 삭제했습니다. ", managerProductService.deleteProductIngTheme(paramDTO));
    }
    @GetMapping("/products/order")
    public ResponseEntity<?> orderProduct(){
        return ResponseMessage.SuccessResponse("주문한 상품을 불러왔습니다. ", managerProductService.getOrdersByMemberId());
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable long productId){
        return ResponseMessage.SuccessResponse("상품 상세정보를 불러왔습니다. ", managerProductService.getProductDetail(productId));
    }

    @PutMapping("/products")
    public ResponseEntity<?> modifyProduct(@RequestBody ManagerProductDTO paramDTO){
        return ResponseMessage.SuccessResponse("상품을 수정했습니다. ", managerProductService.modifyProduct(paramDTO));
    }
    // 상품 수정하기, 주문 상태여부 수정 //
}