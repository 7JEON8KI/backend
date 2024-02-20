package com.hyundai.domain.like.controller;

import com.hyundai.domain.like.service.LikeService;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/**
 * author : 변형준
 * fileName : LikeController
 * since : 2/15/24
 */


@Slf4j
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 좋아요 등록
    @PostMapping("/{productId}")
    public ResponseEntity<?> saveLike(@PathVariable int productId) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("좋아요 등록 요청 memberId :: " + memberId);
        log.debug("좋아요 등록 요청 productId :: " + productId);
        // 좋아요가 눌러진 상태라면 좋아요 취소
        Boolean result = likeService.saveLike(productId, memberId);
        if(result) {
            return ResponseMessage.SuccessResponse("좋아요 등록 성공", true);
        }
        return ResponseMessage.SuccessResponse("좋아요 삭제 성공", false);
    }

    // 좋아요 리스트 조회
    @GetMapping
    public ResponseEntity<?> findLikeList() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("좋아요 리스트 조회 요청 memberId :: " + memberId);
        return ResponseMessage.SuccessResponse("좋아요 리스트 조회 성공", likeService.findLikeList(memberId));
    }
}
