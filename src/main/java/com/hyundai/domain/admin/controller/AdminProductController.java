package com.hyundai.domain.admin.controller;

import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.admin.dto.AdminProductDTO;
import com.hyundai.domain.admin.dto.AdminProductParamDTO;
import com.hyundai.domain.admin.service.AdminProductService;
import com.hyundai.global.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : 강은구
 * @fileName : AdminProductController
 * @description :
 * @since : 02/19/2024
 */
@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminProductController {
    @Autowired
    private AdminProductService adminProductService;
    @PostMapping("/product/{pageNum}")
    public ResponseEntity getProductList(HttpServletRequest request,
                                         @PathVariable("pageNum") Long pageNum, @RequestBody AdminProductParamDTO paramDTO){
    //보류
        return ResponseMessage.SuccessResponse(pageNum + "페이지 조회에 성공했습니다", adminProductService.getProductByPage(paramDTO));
    }
    @PostMapping("/product")
    public ResponseEntity getProductDetail(HttpServletRequest request, @RequestBody AdminProductDTO productDTO){
        log.info("test_product_id           " + productDTO.getProductId());
        return ResponseMessage.SuccessResponse(" ",  adminProductService.getProductDetail(productDTO.getProductId()));
    }

    @PostMapping("/product/delete")
    public ResponseEntity deleteProduct(HttpServletRequest request, @PathVariable("pageNum") Long pageNum){

        return ResponseMessage.SuccessResponse(" ", " ");
    }

    @PostMapping("/product/test")
    public ResponseEntity test(@RequestBody Map<String, Object> map){
        log.info(map.keySet().toString());
        return ResponseMessage.SuccessResponse(" ", adminProductService.test(map));
    }
}
