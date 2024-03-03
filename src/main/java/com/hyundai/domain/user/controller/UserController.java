package com.hyundai.domain.user.controller;

import com.hyundai.domain.user.service.UserService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 강은구
 * @fileName : UserController
 * @description :
 * @since : 03/03/2024
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/banner")
    public ResponseEntity<?> getBanner(){
        return ResponseMessage.SuccessResponse("배너 조회 성공", userService.getBannerList());
    }
}
