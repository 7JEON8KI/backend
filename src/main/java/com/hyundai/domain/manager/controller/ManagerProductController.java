package com.hyundai.domain.manager.controller;

import com.hyundai.domain.manager.dto.ManagerProductDTO;
import com.hyundai.domain.manager.service.ManagerProductService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/products/insert")
    public ResponseEntity<?> insertProduct(@RequestBody ManagerProductDTO paramDTO){

        return ResponseMessage.SuccessResponse(paramDTO.getProductName() + " 상품을 등록했습니다.", managerProductService.insertProduct(paramDTO));
    }

    @GetMapping("/products/{memberId}")
    public ResponseEntity<?> getProductByID(@PathVariable String memberId){

        return ResponseMessage.SuccessResponse(" ", " ");
    }

}
