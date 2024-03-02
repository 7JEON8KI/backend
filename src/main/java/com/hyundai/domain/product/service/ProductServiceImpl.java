package com.hyundai.domain.product.service;

import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.product.dto.request.ProductCriteria;
import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.product.entity.Product;
import com.hyundai.domain.product.repository.ProductSearchRepository;
import com.hyundai.global.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hyundai.domain.product.dto.response.ProductResponseDTO.listOf;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductMapper productMapper;
    private final ProductSearchRepository productSearchRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    @Transactional(readOnly = true)
    // product나 product_like가 바뀌면 캐시 삭제 해줘야 함
    // (product가 바뀌면 allEntries = true, product_like가 바뀌면 해당 key(memberId) 캐시 삭제)
//    @Cacheable(cacheNames = "ProductResponseDTOs", key = "#memberId != null ? #memberId : 'anonymous'")
    public List<ProductResponseDTO> getProducts(ProductCriteria productCriteria, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("themeName", productCriteria.getThemeName());
        params.put("sort", productCriteria.getSort());
        params.put("pageNum", productCriteria.getPageNum());
        params.put("pageAmount", "12");
        params.put("includeSoldOut", productCriteria.getIncludeSoldOut());
        params.put("cursor", null);

        productMapper.findAll(params);
        return (List<ProductResponseDTO>) params.get("cursor");
    }

    @Override
    public ProductResponseDTO getProductDetail(Long productId) {
        // todo product에서 이 코드 반복됨
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
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getSearchProducts(SearchRequestDTO searchDTO, String memberId) {
        // Elasticsearch를 통한 상품 검색
        List<Product> products;
        products = productSearchRepository.search(searchDTO, elasticsearchTemplate);
        return listOf(products);
    }

    @Override
    public List<ProductResponseDTO> getWineProducts(ProductCriteria productCriteria, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("sort", productCriteria.getSort());
        params.put("pageNum", productCriteria.getPageNum());
        params.put("pageAmount", "12");
        params.put("includeSoldOut", productCriteria.getIncludeSoldOut());
        params.put("cursor", null);

        productMapper.findWineAll(params);
        return (List<ProductResponseDTO>) params.get("cursor");
    }
    @Override
    public List<ProductResponseDTO> getThemeProducts(ProductCriteria productCriteria, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("themeName", productCriteria.getThemeName());
        params.put("sort", productCriteria.getSort());
        params.put("pageNum", productCriteria.getPageNum());
        params.put("pageAmount", "12");
        params.put("includeSoldOut", productCriteria.getIncludeSoldOut());
        params.put("cursor", null);

        productMapper.findThemeProducts(params);
        return (List<ProductResponseDTO>) params.get("cursor");
    }

}
