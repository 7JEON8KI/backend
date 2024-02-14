package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.utils.file.DownExcelView;
import com.hyundai.global.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminMapper adminMapper;

    private final DownExcelView downExcelView;

    @Override
    @Transactional(readOnly = true)
    public List<AdminMemberDTO> getMemberList(AdminMemberParamDTO paramDTO) {
        return adminMapper.getListByParams(paramDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminMemberDTO> getMemberListByPage(AdminMemberParamDTO paramDTO, Long pageNum) {
        paramDTO.setPageNum(pageNum);
        return adminMapper.getListByParams(paramDTO);
    }

    @Override
    public void modifyMember(AdminMemberDTO member) {
        adminMapper.modifyMember(member);
    }

    @Override
    public void deleteMember(AdminMemberDTO member) {
        adminMapper.deleteMember(member);
    }

    @Override
    public void changeMemberAuthorization(AdminMemberDTO member) {
        adminMapper.grantAuthorization(member);
    }

    @Override
    public void getMemberExcelFile(HttpServletResponse response) throws IOException {
        downExcelView.makeWorkbook(adminMapper.getAllMembers(), "회원 목록", response);
    }

    @Override
    public List<AdminMemberDTO> searchMembers(String word) {
        adminMapper.searchMembers(word);
        return null;
    }

    @Override
    public AdminMemberDTO getMemberDetail(Long memberId) {
        adminMapper.getMemberDetail(memberId);
        return null;
    }

}

