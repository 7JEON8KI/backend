package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminProductDTO;
import com.hyundai.domain.admin.dto.AdminProductParamDTO;
import com.hyundai.domain.admin.dto.AdminThemeDTO;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.AdminProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 강은구
 * @fileName : AdminProductServiceImpl
 * @description :
 * @since : 02/19/2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

    private final AdminProductMapper adminProductMapper;

    @Override
    public List<AdminProductDTO> getAllProduct() {
        return adminProductMapper.getAllProduct();
    }

    @Override
    public List<AdminProductDTO> getProductByPage(AdminProductParamDTO paramDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum",paramDTO.getPageNum());
        params.put("pageAmount",paramDTO.getPageAmount());
        params.put("standard",paramDTO.getStandard());
        params.put("sort",paramDTO.getSort());


        // 프로시저 커서 결과 변수명
        params.put("result", null);

        adminProductMapper.getProductByPage(params);

        List<AdminProductDTO> list = (List<AdminProductDTO>) params.get("result");
        log.info("size : " + list.size());
        //출력
        for (int i=0; i< list.size(); i++){
            log.info("cursor :   "+list.get(i).toString());
        }
        // 초과된 페이지를 호출했을 경우
        if(list.isEmpty()){
            throw new GlobalException(GlobalErrorCode.NOT_HAVING_DATA);
        }
        return list;
    }

    @Override
    public AdminProductDTO getProductDetail(Long productId) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("productId", productId);

            // 프로시저 커서 결과 변수명 확인용
            params.put("result", null);

            adminProductMapper.getProductDetail(params);
            List<AdminProductDTO> list = (List<AdminProductDTO>) params.get("result");

            return list.get(0);
        }
        catch (Exception e
        ){
            log.info(e.getMessage());
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        try{
            adminProductMapper.deleteProduct(productId);
        } catch (Exception e
        ){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public void modifyProduct(AdminProductDTO paramDTO) {
        try{
            adminProductMapper.modifyProduct(paramDTO);
        } catch (Exception e
        ){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public void addTheme(AdminThemeDTO params) {
        int result = adminProductMapper.addTheme(params);
        if (result==0){
            throw new GlobalException(GlobalErrorCode.NOT_COMPLETED_SQL);
        }
    }

    @Override
    public void deleteTheme(AdminThemeDTO params) {
        int result = adminProductMapper.deleteTheme(params.getThemeId());

        if(result==0){
            throw new GlobalException(GlobalErrorCode.NOT_COMPLETED_SQL);
        }
    }

    @Override
    public void modifyTheme(AdminThemeDTO params) {
        int result = adminProductMapper.modifyTheme(params);

        if(result==0){
            throw new GlobalException(GlobalErrorCode.NOT_COMPLETED_SQL);
        }
    }
}
