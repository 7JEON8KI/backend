package com.hyundai.domain.product.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientExpansion {
    private static final Map<String, List<String>> ingredientExpansions = new HashMap<>();

    static {
        ingredientExpansions.put("소고기", List.of("소", "소고기", "쇠고기", "소뼈", "소곱창","한우", "양념육", "소머리", "소갈비", "소내장", "대창", "곱창", "막창", "소정육", "분쇄가공육제품"));
        ingredientExpansions.put("닭고기", List.of("닭고기", "닭", "닭다리", "닭가슴살", "닭발", "닭넓적다리", "닭다리살"));
        ingredientExpansions.put("돼지고기", List.of("돼지", "돼지고기", "돼지갈비", "삼겹살", "앞다리살", "오겹살", "돼지국밥", "양념육","분쇄가공육제품" ));
        ingredientExpansions.put("야채", List.of("배추", "청경채잎", "혼합야채", "김치", "과.채가공품", "부추", "양배추", "들깻잎", "당근", "양파", "대파", "토마토열매", "쥬키니호박", "건조토마토"));
        ingredientExpansions.put("면", List.of("생면", "건면", "숙면"));
        ingredientExpansions.put("생선", List.of("대구곤이", "명태", "명태알", "어묵", "건어포", "멸치"));
        ingredientExpansions.put("조개", List.of("홍합", "바지락살"));
        ingredientExpansions.put("버섯", List.of("새송이버섯", "팽이버섯", "표고버섯", "느타리버섯", "표고버섯가루"));
        ingredientExpansions.put("새우", List.of("흰다리새우"));
        ingredientExpansions.put("치즈", List.of("자연치즈"));
        ingredientExpansions.put("유제품", List.of("우유", "치즈", "자연치즈"));
    }

    private static final Map<String, List<String>> UnwandedExpansions = new HashMap<>();

    static {
        UnwandedExpansions.put("소고기", List.of("소", "소고기", "쇠고기", "소뼈", "소곱창", "한우", "양념육", "소머리", "소갈비", "소내장", "대창", "곱창", "막창", "소정육", "만두", "닭고기", "닭고기", "닭", "닭다리", "닭가슴살", "닭발", "닭넓적다리", "닭다리살", "돼지", "돼지고기", "돼지갈비", "삼겹살", "앞다리살", "오겹살", "돼지국밥", "베이컨류", "소시지", "양념육", "만두", "분쇄가공육제품", "돼지고기", "닭고기", "돼지곱창", "감귤"));
        UnwandedExpansions.put("닭고기", List.of("곱창", "막창", "소정육", "만두", "닭고기", "닭고기", "닭", "닭다리", "닭가슴살", "닭발", "닭넓적다리", "닭다리살", "베이컨류", "소시지", "양념육", "만두", "분쇄가공육제품", "돼지고기", "돼지곱창"));
        UnwandedExpansions.put("돼지고기", List.of("소", "소고기", "쇠고기", "소뼈", "소곱창","한우", "양념육", "소머리", "소갈비", "소내장", "대창", "곱창", "막창", "소정육", "만두", "닭고기", "닭고기", "닭", "닭다리", "닭가슴살", "닭발", "닭넓적다리", "닭다리살", "돼지", "돼지고기", "돼지갈비", "삼겹살", "앞다리살", "오겹살", "돼지국밥", "베이컨류", "소시지", "양념육", "만두", "분쇄가공육제품", "돼지곱창"));
        UnwandedExpansions.put("야채", List.of("배추", "청경채잎", "혼합야채", "김치", "과.채가공품", "부추", "양배추", "들깻잎", "당근", "양파", "대파", "토마토열매", "쥬키니호박", "건조토마토"));
        UnwandedExpansions.put("면", List.of("생면", "건면", "숙면"));
        UnwandedExpansions.put("생선", List.of("대구곤이", "명태", "명태알", "어묵", "건어포", "멸치"));
        UnwandedExpansions.put("조개", List.of("홍합", "바지락살"));
        UnwandedExpansions.put("버섯", List.of("새송이버섯", "팽이버섯", "표고버섯", "느타리버섯", "표고버섯가루"));
        UnwandedExpansions.put("새우", List.of("흰다리새우"));
        UnwandedExpansions.put("치즈", List.of("자연치즈"));
        UnwandedExpansions.put("유제품", List.of("우유", "치즈", "자연치즈"));
    }
    public static List<String> getExpandedIngredients(String ingredient) {
        return ingredientExpansions.getOrDefault(ingredient, List.of(ingredient));
    }

    public static List<String> getUnwandedExpanded(String ingredient) {
            return UnwandedExpansions.getOrDefault(ingredient, List.of(ingredient));
        }
}
