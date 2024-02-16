package com.hyundai.global.mapper;

import com.hyundai.domain.like.dto.LikeProductListResponseDto;
import com.hyundai.domain.like.entity.Like;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : 변형준
 * @fileName : LikeMapper
 * @since : 2/15/24
 */
public interface LikeMapper {
    void saveLike(Map<String, Object> map);
    void deleteLike(Map<String, Object> map);
    Optional<Like> findLike(Map<String, Object> map);
    List<LikeProductListResponseDto> findLikeList(String memberId);
}

