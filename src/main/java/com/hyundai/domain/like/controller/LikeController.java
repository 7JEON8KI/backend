package com.hyundai.domain.like.controller;

import com.hyundai.domain.like.service.LikeService;
import com.hyundai.global.message.ResponseMessage;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 변형준
 * @fileName : LikeController
 * @since : 2/15/24
 */


@Log4j
@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // 좋아요 등록
    @PostMapping("/{productId}")
    public ResponseEntity saveLike(HttpServletRequest request, @PathVariable int productId) {
        String memberId = request.getAttribute("memberId").toString();
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
    public ResponseEntity findLikeList(HttpServletRequest request) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("좋아요 리스트 조회 요청 memberId :: " + memberId);
        return ResponseMessage.SuccessResponse("좋아요 리스트 조회 성공", likeService.findLikeList(memberId));
    }
}
