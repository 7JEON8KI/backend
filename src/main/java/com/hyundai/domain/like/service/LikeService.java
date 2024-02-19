package com.hyundai.domain.like.service;

import com.hyundai.domain.like.dto.LikeProductListResponseDto;
import java.util.List;

/**
 * @author : 변형준
 * @fileName : LikeService
 * @since : 2/15/24
 */
public interface LikeService {
    Boolean saveLike(int productId, String memberId);
    List<LikeProductListResponseDto> findLikeList(String memberId);
}
