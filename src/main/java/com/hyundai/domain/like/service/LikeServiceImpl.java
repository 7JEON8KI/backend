package com.hyundai.domain.like.service;

import com.hyundai.domain.like.dto.LikeProductListResponseDto;
import com.hyundai.global.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author : 변형준
 * fileName : LikeServiceImpl
 * since : 2/15/24
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeMapper likeMapper;

    @Override
    // product_like 바뀌면 해당 key(memberId) 캐시 삭제
    @CacheEvict(cacheNames = "ProductResponseDTOs", key = "#memberId")
    public Boolean saveLike(int productId, String memberId) {
        Map<String, Object> map = Map.of("productId", productId, "memberId", memberId);
        if(likeMapper.findLike(map).isEmpty()) {
            likeMapper.saveLike(map);
            return true;
        } else {
            likeMapper.deleteLike(map);
            return false;
        }
    }

    @Override
    public List<LikeProductListResponseDto> findLikeList(String memberId) {
        return likeMapper.findLikeList(memberId);
    }


}
