package com.hyundai.domain.admin.controller;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/member")
    public ResponseEntity<List<AdminMemberDTO>> getMembers(@RequestBody AdminMemberParamDTO paramDTO){
        List<AdminMemberDTO> result =  adminService.getMemberList(paramDTO);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/member/{pageNum}")
    public ResponseEntity<List<AdminMemberDTO>> getMembersByPage(@RequestBody AdminMemberParamDTO paramDTO, @PathVariable("pageNum") Long pageNum){
        List<AdminMemberDTO> result = adminService.getMemberListByPage(paramDTO, pageNum);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PostMapping("/member/modify")
    public ResponseEntity modifyMember(@RequestBody AdminMemberDTO memberDTO){
        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }
    @GetMapping("/member/excelDown")
    public void excelDown(HttpServletResponse response) throws Exception{
        adminService.getMemberExcelFile(response);
    }
}
