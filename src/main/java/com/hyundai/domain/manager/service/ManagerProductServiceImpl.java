package com.hyundai.domain.manager.service;

import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.manager.dto.ManagerOrderProductDTO;
import com.hyundai.domain.manager.dto.ManagerProductDTO;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.ManagerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 강은구
 * @fileName : ManagerProductServiceImpl
 * @description :
 * @since : 02/20/2024
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerProductServiceImpl implements ManagerProductService{

    private final ManagerMapper managerMapper;
    @Override
    public String insertProduct(ManagerProductDTO productDTO) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();

        ManagerProductDTO data = ManagerProductDTO.builder()
                .productId(productDTO.getProductId()) // productId는 sequence로 자동 생성
                .memberId(memberId)
                .productName(productDTO.getProductName())
                .productSubName(productDTO.getProductSubName())
                .price(productDTO.getPrice())
                .productType(productDTO.getProductType())
                .stock(productDTO.getStock())
                .discountRate(productDTO.getDiscountRate())
                .productDetail(productDTO.getProductDetail())
                .amount(productDTO.getAmount())
                .calorie(productDTO.getCalorie())
                .storage(productDTO.getStorage())
                .thumbnailImageUrl(productDTO.getThumbnailImageUrl())
                .build();
        try{
            managerMapper.insertProduct(data);
            return " ";
        }
        catch (Exception e){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public List<ManagerProductDTO> getProductByMemberId() {
        String loginMemberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", loginMemberId);
        params.put("result", null);
        log.info("result : " + params.get("result"));
        log.info("memberId : " + params.get("memberId"));
        managerMapper.getProductByMemberId(params);
        log.info("result : " + params.get("result"));
        List<ManagerProductDTO> results = (List<ManagerProductDTO>) params.get("result");
        return results;
    }

    @Override
    public String deleteProduct(Map<String,Object> params) {
        String loginMemberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();

        params.put("memberId", loginMemberId);

        try {
            managerMapper.deleteProduct(params);
            return " ";
        }
        catch (Exception e){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }

    }

    @Override
    public String addProductIngTheme(Map<String,Object> params) {
        String loginMemberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();

        params.put("memberId", loginMemberId);
        log.info("params : " + params.get("ingredientIdArray"));
        log.info("params : " + params.get("productId"));
        try{
            managerMapper.addProductIngredients(params);
            managerMapper.addProductTheme(params);
            return " ";
        }
        catch (Exception e){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public String deleteProductIngTheme(Map<String, Object> params) {
        String loginMemberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        params.put("memberId", loginMemberId);
        try {
            managerMapper.deleteProductIngTheme(params);
            return " ";
        }
        catch (Exception e){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }


    @Override
    public List<ManagerOrderProductDTO> getOrdersByMemberId() {
        String loginMemberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();

        Map<String, Object> params = new HashMap<>();
        params.put("memberId", loginMemberId);
        params.put("result", null);
        try {
            managerMapper.getOrdersByMemberId(params);
            log.info("result : " + params.get("result"));
            List<ManagerOrderProductDTO> results = (List<ManagerOrderProductDTO>) params.get("result");
            return results;
        }
        catch (Exception e){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }
}
