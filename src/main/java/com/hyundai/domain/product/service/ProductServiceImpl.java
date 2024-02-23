package com.hyundai.domain.product.service;

import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.global.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDTO> getProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = null;
        log.debug("authentication : " + authentication);
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
            memberId = userDetails.getMemberId();
            log.debug("memberId : " + memberId);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("action", "FIND_ALL");
        params.put("cursor", null);
        productMapper.findAll(params);
        return (List<ProductResponseDTO>) params.get("cursor");
    }


    @Override
    public ProductResponseDTO getProductDetail(Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = null;
        log.debug("authentication : " + authentication);
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
            memberId = userDetails.getMemberId();
            log.debug("memberId : " + memberId);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("productId", productId);
        params.put("action", "FIND_BY_ID");
        params.put("cursor", null);

        productMapper.findById(params);
        List<ProductResponseDTO> list = (List<ProductResponseDTO>) params.get("cursor");
        return list.get(0);
    }

    @Override
    public List<ProductResponseDTO> getThemeProducts(String themeName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberId = null;
        log.debug("authentication : " + authentication);
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
            memberId = userDetails.getMemberId();
            log.debug("memberId : " + memberId);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("themeName", themeName);
        params.put("action", "FIND_BY_THEME");
        params.put("cursor", null);

        productMapper.findByTheme(params);
        return (List<ProductResponseDTO>) params.get("cursor");
    }
}
