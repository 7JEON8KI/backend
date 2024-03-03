package com.hyundai.domain.product.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.product.dto.request.ProductCriteria;
import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.dto.response.ProductResponseDTO;
import com.hyundai.domain.product.dto.response.ProductTotalDTO;
import com.hyundai.domain.product.dto.response.ProductWithCountResponseDTO;
import com.hyundai.domain.product.dto.response.RecommendProducts;
import com.hyundai.domain.product.entity.Product;
import com.hyundai.domain.product.repository.ProductSearchRepository;
import com.hyundai.global.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
    public ProductWithCountResponseDTO getProducts(ProductCriteria productCriteria, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("themeName", productCriteria.getThemeName());
        params.put("sort", productCriteria.getSort());
        params.put("pageNum", productCriteria.getPageNum());
        params.put("pageAmount", productCriteria.getPageAmount());
        params.put("includeSoldOut", productCriteria.getIncludeSoldOut());

        productMapper.findAll(params);
        List<ProductResponseDTO> list = (List<ProductResponseDTO>) params.get("cursor");

        productMapper.findProductsCount(params);
        List<ProductTotalDTO> totalList = (List<ProductTotalDTO>) params.get("total");
        int total = totalList.get(0).getTotal();

        ProductWithCountResponseDTO dto = ProductWithCountResponseDTO.builder()
                .productResponseDTOList(list)
                .total(total)
                .build();
        return dto;
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
    public ProductWithCountResponseDTO getWineProducts(ProductCriteria productCriteria, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("sort", productCriteria.getSort());
        params.put("pageNum", productCriteria.getPageNum());
        params.put("pageAmount", productCriteria.getPageAmount());
        params.put("includeSoldOut", productCriteria.getIncludeSoldOut());
        params.put("cursor", null);

        productMapper.findWineAll(params);
        List<ProductResponseDTO> list = (List<ProductResponseDTO>) params.get("cursor");

        productMapper.findWineProductsCount(params);
        List<ProductTotalDTO> totalList = (List<ProductTotalDTO>) params.get("total");
        int total = totalList.get(0).getTotal();

        ProductWithCountResponseDTO dto = ProductWithCountResponseDTO.builder()
                .productResponseDTOList(list)
                .total(total)
                .build();
        return dto;

    }
    @Override
    public ProductWithCountResponseDTO getThemeProducts(ProductCriteria productCriteria, String memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("themeName", productCriteria.getThemeName());
        params.put("sort", productCriteria.getSort());
        params.put("pageNum", productCriteria.getPageNum());
        params.put("pageAmount", productCriteria.getPageAmount());
        params.put("includeSoldOut", productCriteria.getIncludeSoldOut());

        productMapper.findThemeProducts(params);
        List<ProductResponseDTO> list = (List<ProductResponseDTO>) params.get("cursor");
        log.debug("list : " + list);
        productMapper.findThemeProductsCount(params);
        List<ProductTotalDTO> totalList = (List<ProductTotalDTO>) params.get("total");
        int total = totalList.get(0).getTotal();
        ProductWithCountResponseDTO dto = ProductWithCountResponseDTO.builder()
                .productResponseDTOList(list)
                .total(total)
                .build();
        return dto;
    }

    @Override
    public List<RecommendProducts> getImageSearchProducts(MultipartFile image) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String fastApiUrl = "http://localhost:8000/ai/image-search";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("file", Base64.getEncoder().encodeToString(image.getBytes()));

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(fastApiUrl, HttpMethod.POST, entity, String.class);

        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();
        List<RecommendProducts> products = objectMapper.readValue(response.getBody(), new TypeReference<List<RecommendProducts>>(){});
        return products;
    }

}
