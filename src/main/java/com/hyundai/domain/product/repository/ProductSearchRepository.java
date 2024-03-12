package com.hyundai.domain.product.repository;

import com.hyundai.domain.product.dto.IngredientExpansion;
import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.entity.Product;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.*;


/**
 * author : 이소민
 */

public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
    default List<Product> search(SearchRequestDTO searchRequestDTO, ElasticsearchOperations elasticsearchOperations) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("productType", "밀키트"));

        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> functionBuilders = new ArrayList<>();
        // 선호하는 재료에 대한 조건 추가
        List<String> preferredIngredients = searchRequestDTO.getPreferredIngredients();
        if (preferredIngredients != null) {
            for (String ingredient : preferredIngredients) {
                List<String> expandedIngredients = IngredientExpansion.getExpandedIngredients(ingredient);
                for (String expandedIngredient : expandedIngredients) {
                    boolQueryBuilder.should(QueryBuilders.matchQuery("ingredients", expandedIngredient));
                    boolQueryBuilder.should(QueryBuilders.matchQuery("productName", expandedIngredient));
                    functionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                            QueryBuilders.matchQuery("ingredients", expandedIngredient),
                            ScoreFunctionBuilders.weightFactorFunction(1.0f) // 가중치 조정
                    ));
                }
            }
        }

        // 원하지 않는 재료에 대한 조건 추가
        List<String> unwantedIngredients = searchRequestDTO.getUnwantedIngredients();
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("productName", "온가족")); // todo : 데이터 조정
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("productName", "씨월드호떡"));
        if (unwantedIngredients != null) {
            for (String ingredient : unwantedIngredients) {
                List<String> expandedIngredients = IngredientExpansion.getUnwandedExpanded(ingredient);
                for (String expandedIngredient : expandedIngredients) {
                    boolQueryBuilder.mustNot(QueryBuilders.matchQuery("ingredients", expandedIngredient));
                }
                String wildcardPattern = "*" + ingredient + "*";
                boolQueryBuilder.mustNot(QueryBuilders.wildcardQuery("productName", wildcardPattern));
            }
        }

        // 키워드에 대한 조건 추가
        String keyword = searchRequestDTO.getKeyword();
        if (keyword != null) {
            BoolQueryBuilder keywordQueryBuilder = QueryBuilders.boolQuery();
            List<String> expandedIngredients = IngredientExpansion.getExpandedIngredients(keyword);
            for (String expandedIngredient : expandedIngredients) {
                keywordQueryBuilder.should(QueryBuilders.matchQuery("productName", expandedIngredient));
                keywordQueryBuilder.should(QueryBuilders.matchQuery("productSubName", expandedIngredient));
                keywordQueryBuilder.should(QueryBuilders.matchQuery("ingredients", expandedIngredient));
            }
            boolQueryBuilder.must(keywordQueryBuilder);
        }

        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                boolQueryBuilder,
                functionBuilders.toArray(new FunctionScoreQueryBuilder.FilterFunctionBuilder[0])
        ).scoreMode(FunctionScoreQuery.ScoreMode.SUM); // 점수 모드 설정

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(functionScoreQueryBuilder)
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC)) // 점수가 높은 순으로 정렬
                .build();

//        return elasticsearchOperations.queryForList(searchQuery, Product.class);

        // Elasticsearch로부터 결과 조회
        List<Product> products = elasticsearchOperations.queryForList(searchQuery, Product.class);

        // "productName"의 앞 20글자가 같은 항목 필터링
        return filterProductsByProductNameOverlap(products);
    }

    private List<Product> filterProductsByProductNameOverlap(List<Product> products) {
        if (products == null || products.isEmpty()) return Collections.emptyList();

        // 중복 확인을 위한 Map 초기화 (Key: productName의 앞 20글자, Value: Product 객체)
        Map<String, Product> uniqueProducts = new HashMap<>();

        for (Product product : products) {
            String productName = product.getProductName();
            if (productName == null || productName.isEmpty()) continue;

            // productName의 앞 20글자 추출
            String productNamePrefix = productName.length() > 20 ? productName.substring(0, 20) : productName;

            // 앞 20글자가 동일한 상품이 이미 Map에 없는 경우만 추가
            uniqueProducts.putIfAbsent(productNamePrefix, product);
        }

        // 중복되지 않는 상품들만 리스트로 반환
        return new ArrayList<>(uniqueProducts.values());
    }
}
