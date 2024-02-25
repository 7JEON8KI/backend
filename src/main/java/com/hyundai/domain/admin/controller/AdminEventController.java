package com.hyundai.domain.admin.controller;

import com.hyundai.domain.admin.dto.AdminEventDTO;
import com.hyundai.domain.admin.service.AdminEventService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminEventController {
    private final AdminEventService adminEventService;

    @GetMapping("/event")
    public ResponseEntity<?> getEventList(){
        return ResponseMessage.SuccessResponse("이벤트 조회 성공", adminEventService.getEventList());
    }

    @PostMapping("/event")
    public ResponseEntity<?> insertEvent(@RequestBody AdminEventDTO paramDTO){
        return ResponseMessage.SuccessResponse("이벤트 생성 성공", adminEventService.insertEvent(paramDTO));
    }

    @PostMapping("/event/modify")
    public ResponseEntity<?> modifyEvent(@RequestBody AdminEventDTO paramDTO){
        return ResponseMessage.SuccessResponse("이벤트 수정 성공", adminEventService.modifyEvent(paramDTO));
    }

    @PutMapping("/event")
    public ResponseEntity<?> deleteEvent(@RequestBody AdminEventDTO paramDTO){
        return ResponseMessage.SuccessResponse("이벤트 삭제 성공", adminEventService.deleteEvent(paramDTO));
    }

}
