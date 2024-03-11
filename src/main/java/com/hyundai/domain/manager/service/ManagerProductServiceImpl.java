package com.hyundai.domain.manager.service;

import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.manager.dto.ManagerOrderProductDTO;
import com.hyundai.domain.manager.dto.ManagerProductDTO;
import com.hyundai.domain.manager.dto.ManagerTop5ProductDTO;
import com.hyundai.domain.utils.dto.OrderListDeliveryDTO;
import com.hyundai.domain.utils.file.DownExcelView;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.ExcelMapper;
import com.hyundai.global.mapper.ManagerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private final ExcelMapper excelMapper;
    private final DownExcelView downExcelView;
    @Override
    public String insertProduct(ManagerProductDTO productDTO) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();

        ManagerProductDTO data = ManagerProductDTO.builder()// productId는 sequence로 자동 생성
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
            log.info("error : " + e.getMessage());
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public ManagerProductDTO getProductDetail(long productId) {
        return managerMapper.getProductDetail(productId);
    }

    @Override
    public String modifyProduct(ManagerProductDTO productDTO) {
        try {
            managerMapper.modifyProduct(productDTO);
            return " ";
        }
        catch (Exception e){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public List<ManagerTop5ProductDTO> getTop5Product() {
        String loginMemberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        return managerMapper.getTop5Product(loginMemberId);
    }

    @Override
    public void downOrderExcel(HttpServletResponse response) {
        String loginMemberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();

        Map<String, Object> params = new HashMap<>();
        params.put("memberId", loginMemberId);
        params.put("result", null);
        try {
            excelMapper.getOrderByDelivery(params);
            log.info("result : " + params.get("result"));
            List<OrderListDeliveryDTO> results = (List<OrderListDeliveryDTO>) params.get("result");
            LocalDate currentDate = LocalDate.now();
            log.info(currentDate.toString());
            log.info(results.get(1).toString());
            // 날짜를 문자열로 변환하기 위해 포맷 지정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 현재 날짜를 지정된 포맷으로 변환하여 문자열 변수에 할당
            String dateString = currentDate.format(formatter);
            downExcelView.orderExcel(results, dateString +"주문목록.xls", response) ;
        }
        catch (Exception e){
            log.info("error : " + e.getMessage());
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }

    }
}
