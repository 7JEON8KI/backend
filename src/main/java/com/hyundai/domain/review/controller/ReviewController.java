package com.hyundai.domain.review.controller;

import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.domain.review.dto.request.ReviewRequestDto;
import com.hyundai.domain.review.service.ReviewService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * author : 변형준
 * fileName : ReviewController
 * since : 2/18/24
 */

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록 및 수정
    @PostMapping
    public ResponseEntity<?> saveReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("리뷰 등록 요청 memberId :: " + memberId);
        log.debug("리뷰 등록 요청 productId :: " + reviewRequestDto.getProductId());
        reviewService.saveReview(reviewRequestDto, memberId);
        return ResponseMessage.SuccessResponse("리뷰 등록 성공", "");
    }

    // 리뷰 업데이트
    @PutMapping
    public ResponseEntity<?> updateReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("리뷰 수정 요청 memberId :: " + memberId);
        log.debug("리뷰 수정 요청 productId :: " + reviewRequestDto.getProductId());
        reviewService.updateReview(reviewRequestDto, memberId);
        return ResponseMessage.SuccessResponse("리뷰 수정 성공", "");
    }
    // 리뷰 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("리뷰 삭제 요청 memberId :: " + memberId);
        log.debug("리뷰 삭제 요청 productId :: " + reviewRequestDto.getProductId());
        reviewService.deleteReview(reviewRequestDto, memberId);
        return ResponseMessage.SuccessResponse("리뷰 삭제 성공", "");
    }

    // 단일 상품에 대한 내가 쓴 리뷰 조회
    @GetMapping("/member/{productId}")
    public ResponseEntity<?> getProductReviewByMemberId(@PathVariable int productId) {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("리뷰 조회 요청 memberId :: " + memberId);
        log.debug("리뷰 조회 요청 productId :: " + productId);
        return ResponseMessage.SuccessResponse("리뷰 조회 성공", reviewService.getProductReviewByMemberId(memberId, productId));
    }

    // 내가 쓴 리뷰 전체 조회
    @GetMapping("/member")
    public ResponseEntity<?> getReviewsByMemberId() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("내가 쓴 리뷰 조회 요청 memberId :: " + memberId);
        return ResponseMessage.SuccessResponse("내가 쓴 리뷰 조회 성공", reviewService.getReviewsByMemberId(memberId));
    }

//    // 상품에 대한 리뷰 전체 조회
//    @GetMapping("/product/{productId}")
//    public ResponseEntity<?> getProductReviews(@PathVariable int productId) {
//        log.debug("상품에 대한 리뷰 조회 요청 productId :: " + productId);
//        return ResponseMessage.SuccessResponse("상품에 대한 리뷰 조회 성공", reviewService.getProductReviews(productId));
//    }

    // 상품에 대한 리뷰 전체 조회
    @GetMapping("/product/{productId}/page/{pageNum}")
    public ResponseEntity<?> getProductReviews(@PathVariable int productId, @PathVariable int pageNum) {
        log.debug("상품에 대한 리뷰 조회 요청 productId :: " + productId);
        return ResponseMessage.SuccessResponse("상품에 대한 리뷰 조회 성공", reviewService.getProductReviews(productId, pageNum));
    }

    //리뷰 작성이 가능한 상품 조회
    @GetMapping("/able")
    public ResponseEntity<?> getAbleReviewProduct() {
        String memberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        log.debug("리뷰 작성 가능한 상품 조회 요청 memberId :: " + memberId);
        return ResponseMessage.SuccessResponse("리뷰 작성 가능한 상품 조회 성공", reviewService.getAbleReviewProduct(memberId));
    }

}
