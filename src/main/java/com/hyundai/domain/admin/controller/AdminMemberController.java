package com.hyundai.domain.admin.controller;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.admin.service.AdminMemberService;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    @GetMapping("/member")
    public ResponseEntity getMembers(){
        return ResponseMessage.SuccessResponse("회원 조회 성공", adminMemberService.getMemberList());
    }

    @PostMapping("/member/{pageNum}")
    public ResponseEntity getMembersByPage(@RequestBody AdminMemberParamDTO paramDTO, @PathVariable("pageNum") Long pageNum){
        return ResponseMessage.
                SuccessResponse(pageNum + "페이지 조회 성공", adminMemberService.getMemberListByPage(paramDTO, pageNum));
    }
    @PostMapping("/member/modify")
    public ResponseEntity modifyMember(@RequestBody AdminMemberDTO memberDTO){
        return ResponseMessage
                .SuccessResponse(memberDTO.getMemberId() + "회원 수정 성공", adminMemberService.modifyMember(memberDTO));
    }

    @DeleteMapping("/member/delete")
    public ResponseEntity deleteMember(@RequestBody AdminMemberDTO memberDTO){
        return ResponseMessage
                .SuccessResponse(memberDTO.getMemberId() + "회원 삭제 성공", adminMemberService.deleteMember(memberDTO));
    }
    @PostMapping("/member/auth")
    public ResponseEntity authMember(@RequestBody AdminMemberDTO memberDTO){
        return ResponseMessage
                .SuccessResponse(memberDTO.getMemberId() + " 회원 권한 변경", adminMemberService.changeMemberAuthorization(memberDTO));
    }
    @PostMapping("/member/search")
    public ResponseEntity searchMember(@RequestBody Map<String, String> requestBody){

        return ResponseMessage
                .SuccessResponse(requestBody.get("search") + " 단어 검색 성공", adminMemberService.searchMembers(requestBody.get("search")));
    }
    @GetMapping("/member/detail/{memberId}")
    public ResponseEntity getMemberDetail(@PathVariable String memberId){
        return ResponseMessage
                .SuccessResponse( "회원 조회 성공", adminMemberService.getMemberDetail(memberId));
    }

    @GetMapping("/member/excelDown")
    public void excelDown(HttpServletResponse response) throws Exception{
        adminMemberService.getMemberExcelFile(response);
    }
}
