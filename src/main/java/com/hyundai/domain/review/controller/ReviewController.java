package com.hyundai.domain.review.controller;

import com.hyundai.domain.review.dto.request.ReviewRequestDto;
import com.hyundai.domain.review.service.ReviewService;
import com.hyundai.global.message.ResponseMessage;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 변형준
 * @fileName : ReviewController
 * @since : 2/18/24
 */

@Log4j
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 리뷰 등록 및 수정
    @PostMapping
    public ResponseEntity saveReview(HttpServletRequest request, @RequestBody ReviewRequestDto reviewRequestDto) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("리뷰 등록 요청 memberId :: " + memberId);
        log.debug("리뷰 등록 요청 productId :: " + reviewRequestDto.getProductId());
        String result = reviewService.saveReview(reviewRequestDto, memberId);
        return ResponseMessage.SuccessResponse(result, "");
    }

    // 리뷰 업데이트
    @PutMapping
    public ResponseEntity updateReview(HttpServletRequest request, @RequestBody ReviewRequestDto reviewRequestDto) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("리뷰 등록 요청 memberId :: " + memberId);
        log.debug("리뷰 등록 요청 productId :: " + reviewRequestDto.getProductId());
        String result = reviewService.updateReview(reviewRequestDto, memberId);
        return ResponseMessage.SuccessResponse(result, "");
    }
    // 리뷰 삭제
    @DeleteMapping
    public ResponseEntity deleteReview(HttpServletRequest request, @RequestBody ReviewRequestDto reviewRequestDto) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("리뷰 등록 요청 memberId :: " + memberId);
        log.debug("리뷰 등록 요청 productId :: " + reviewRequestDto.getProductId());
        String result = reviewService.deleteReview(reviewRequestDto, memberId);
        return ResponseMessage.SuccessResponse(result, "");
    }

    // 단일 상품에 대한 내가 쓴 리뷰 조회
    @GetMapping("/my/{productId}")
    public ResponseEntity getProductReviewByMemberId(HttpServletRequest request, @PathVariable int productId) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("리뷰 조회 요청 memberId :: " + memberId);
        log.debug("리뷰 조회 요청 productId :: " + productId);
        return ResponseMessage.SuccessResponse("리뷰 조회 성공", reviewService.getProductReviewByMemberId(memberId, productId));
    }

    // 내가 쓴 리뷰 전체 조회
    @GetMapping("/my")
    public ResponseEntity getReviewsByMemberId(HttpServletRequest request) {
        String memberId = request.getAttribute("memberId").toString();
        log.debug("내가 쓴 리뷰 조회 요청 memberId :: " + memberId);
        return ResponseMessage.SuccessResponse("내가 쓴 리뷰 조회 성공", reviewService.getReviewsByMemberId(memberId));
    }

    // 상품에 대한 리뷰 전체 조회
    @GetMapping("/product/{productId}")
    public ResponseEntity getProductReviews(@PathVariable int productId) {
        log.debug("상품에 대한 리뷰 조회 요청 productId :: " + productId);
        return ResponseMessage.SuccessResponse("상품에 대한 리뷰 조회 성공", reviewService.getProductReviews(productId));
    }

}
