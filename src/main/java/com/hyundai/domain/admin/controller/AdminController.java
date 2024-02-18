package com.hyundai.domain.admin.controller;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.admin.service.AdminService;
import com.hyundai.global.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/member")
    public ResponseEntity getMembers(HttpServletRequest request, @RequestBody AdminMemberParamDTO paramDTO){
        return ResponseMessage.SuccessResponse("회원 조회 성공", adminService.getMemberList(paramDTO));
    }

    @PostMapping("/member/{pageNum}")
    public ResponseEntity getMembersByPage(HttpServletRequest request, @RequestBody AdminMemberParamDTO paramDTO, @PathVariable("pageNum") Long pageNum){
        return ResponseMessage.
                SuccessResponse(pageNum + "페이지 조회 성공", adminService.getMemberListByPage(paramDTO, pageNum));
    }
    @PostMapping("/member/modify")
    public ResponseEntity modifyMember(HttpServletRequest request, @RequestBody AdminMemberDTO memberDTO){
        return ResponseMessage
                .SuccessResponse(memberDTO.getMemberId() + "회원 수정 성공", adminService.modifyMember(memberDTO));
    }

    @PostMapping("/member/delete")
    public ResponseEntity deleteMember(HttpServletRequest request, @RequestBody AdminMemberDTO memberDTO){
        return ResponseMessage
                .SuccessResponse(memberDTO.getMemberId() + "회원 삭제 성공", adminService.deleteMember(memberDTO));
    }
    @PostMapping("/member/auth")
    public ResponseEntity authMember(HttpServletRequest request, @RequestBody AdminMemberDTO memberDTO){
        return ResponseMessage
                .SuccessResponse(memberDTO.getMemberId() + " 회원 권한 변경", adminService.changeMemberAuthorization(memberDTO));
    }
    @PostMapping("/member/search")
    public ResponseEntity searchMember(HttpServletRequest request, @RequestParam String search){
        return ResponseMessage
                .SuccessResponse(search + " 단어 검색 성공", adminService.searchMembers(search));
    }
    @GetMapping("/member/detail/{memberId}")
    public ResponseEntity getMemberDetail(HttpServletRequest request, @PathVariable Long memberId){
        return ResponseMessage
                .SuccessResponse( "회원 조회 성공", adminService.getMemberDetail(memberId));
    }

    @GetMapping("/member/excelDown")
    public void excelDown(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String memberId = request.getAttribute("memberId").toString();
        adminService.getMemberExcelFile(response);
    }
}
