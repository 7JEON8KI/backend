package com.hyundai.domain.product.repository;

import com.hyundai.domain.product.dto.IngredientExpansion;
import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.entity.Product;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
    default List<Product> search(SearchRequestDTO searchRequestDTO, ElasticsearchOperations elasticsearchOperations) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("productType", "밀키트"));

        List<String> preferredIngredients = searchRequestDTO.getPreferredIngredients();
        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> functionBuilders = new ArrayList<>();

        if (preferredIngredients != null) {
            for (String ingredient : preferredIngredients) {
                List<String> expandedIngredients = IngredientExpansion.getExpandedIngredients(ingredient);
                for (String expandedIngredient : expandedIngredients) {
                    boolQueryBuilder.should(QueryBuilders.matchQuery("ingredients", expandedIngredient));
                    functionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                            QueryBuilders.matchQuery("ingredients", expandedIngredient),
                            ScoreFunctionBuilders.weightFactorFunction(1)
                    ));
                }
            }
        }

        List<String> unwantedIngredients = searchRequestDTO.getUnwantedIngredients();
        if (unwantedIngredients != null) {
            unwantedIngredients.stream()
                    .flatMap(ingredient -> IngredientExpansion.getUnwandedExpanded(ingredient).stream())
                    .distinct()
                    .forEach(expandedIngredient -> boolQueryBuilder.mustNot(QueryBuilders.matchQuery("ingredients", expandedIngredient)));
        }

        String keyword = searchRequestDTO.getKeyword();
        if (keyword != null) {
            BoolQueryBuilder keywordQueryBuilder = QueryBuilders.boolQuery();
            keywordQueryBuilder.should(QueryBuilders.matchQuery("productName", keyword));
            keywordQueryBuilder.should(QueryBuilders.matchQuery("productSubName", keyword));
            keywordQueryBuilder.should(QueryBuilders.matchQuery("ingredients", keyword));
            boolQueryBuilder.must(keywordQueryBuilder);
        }

        // 랜덤 점수 함수를 적용한 FunctionScoreQueryBuilder 생성
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                boolQueryBuilder,
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(ScoreFunctionBuilders.randomFunction())
                }
        );

        // 새로운 검색 쿼리를 구성
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(functionScoreQueryBuilder)
                .build();

        // 검색 실행 및 결과 반환
        return elasticsearchOperations.queryForList(searchQuery, Product.class);
    }
}
