package com.hyundai.domain.product.service;

import com.hyundai.domain.product.dto.request.ProductRequestDTO;
import com.hyundai.domain.product.dto.response.ProductResposneDTO;
import com.hyundai.domain.product.entity.Product;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResposneDTO> getProducts(String memberId) {
        List<Product> products = productMapper.findAll();
        if(products != null) {
            return ProductResposneDTO.listOf(products);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResposneDTO getProductDetail(Long productId, String memberId) {
        Product product = productMapper.findById(productId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.PRODUCT_NOT_FOUND)
        );
        return new ProductResposneDTO(product);
    }
}
