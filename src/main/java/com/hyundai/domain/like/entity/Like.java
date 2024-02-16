package com.hyundai.domain.like.entity;

import lombok.*;

/**
 * @author : 변형준
 * @fileName : Like
 * @since : 2/15/24
 */

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    private int likeId;
    private int productId;
    private String memberId;

}
