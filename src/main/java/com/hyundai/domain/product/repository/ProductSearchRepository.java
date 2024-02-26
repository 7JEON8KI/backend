package com.hyundai.domain.product.repository;

import com.hyundai.domain.product.dto.request.SearchRequestDTO;
import com.hyundai.domain.product.entity.Product;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {

    default List<Long> search(SearchRequestDTO searchRequestDTO, ElasticsearchOperations elasticsearchOperations) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // Add conditions for preferred ingredients
        List<String> preferredIngredients = searchRequestDTO.getPreferredIngredients();
        if (preferredIngredients != null) {
            for (String ingredient : preferredIngredients) {
                boolQueryBuilder.must(QueryBuilders.matchQuery("ingredients", ingredient));
            }
        }

        // Add conditions for unwanted ingredients
        List<String> unwantedIngredients = searchRequestDTO.getUnwantedIngredients();
        if (unwantedIngredients != null) {
            for (String ingredient : unwantedIngredients) {
                boolQueryBuilder.mustNot(QueryBuilders.matchQuery("ingredients", ingredient));
            }
        }

        // Add condition for keyword
        String keyword = searchRequestDTO.getKeyword();
        if (keyword != null) {
            BoolQueryBuilder keywordQueryBuilder = QueryBuilders.boolQuery();
            keywordQueryBuilder.should(QueryBuilders.matchQuery("productName", keyword));
            keywordQueryBuilder.should(QueryBuilders.matchQuery("productSubName", keyword));
            keywordQueryBuilder.should(QueryBuilders.matchQuery("ingredients", keyword));
            boolQueryBuilder.must(keywordQueryBuilder);
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return elasticsearchOperations.queryForList(searchQuery, Product.class)
                .stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());
    }
}

