package com.hyundai.domain.admin.controller;

import com.hyundai.domain.admin.dto.AdminBannerDTO;
import com.hyundai.domain.admin.service.AdminBannerService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminBannerController {
    private final AdminBannerService adminBannerService;

    // getBanner, getEvent 부분 공통 API로 빼기?
    @GetMapping("/banner")
    public ResponseEntity<?> getBanner(){
        return ResponseMessage.SuccessResponse("배너 조회 성공", adminBannerService.getBannerList());
    }

    @PostMapping("/banner")
    public ResponseEntity<?> insertBanner(@RequestBody AdminBannerDTO paramDTO){
        return ResponseMessage.SuccessResponse("배너 생성 성공", adminBannerService.insertBanner(paramDTO));
    }
    @PostMapping("/banner/modify")
    public ResponseEntity<?> modifyBanner(@RequestBody AdminBannerDTO paramDTO){
        return ResponseMessage.SuccessResponse("배너 수정 성공", adminBannerService.modifyBanner(paramDTO));
    }
    @PutMapping("/banner")
    public ResponseEntity<?> deleteBanner(@RequestBody AdminBannerDTO memberDTO){
        return ResponseMessage.SuccessResponse("배너 삭제 성공", adminBannerService.deleteBanner(memberDTO));
    }
}