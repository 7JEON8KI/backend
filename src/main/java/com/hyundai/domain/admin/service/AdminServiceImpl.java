package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.utils.file.DownExcelView;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
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
        if(adminMapper.getListByParams(paramDTO).isEmpty()){
            throw new GlobalException(GlobalErrorCode.NOT_HAVING_DATA);
        }

        return adminMapper.getListByParams(paramDTO);
    }

    @Override
    public String modifyMember(AdminMemberDTO member) {
        try{
            adminMapper.modifyMember(member);
        } catch (Exception e
        ){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
        return "성공";
    }

    @Override
    public String deleteMember(AdminMemberDTO member) {
        try{
            adminMapper.deleteMember(member);
            return "성공";
        } catch (Exception e
        ){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public String changeMemberAuthorization(AdminMemberDTO member) {
        try{
            adminMapper.grantAuthorization(member);
            return "성공";
        } catch (Exception e){
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }

    }



    @Override
    public List<AdminMemberDTO> searchMembers(String word) {
        return adminMapper.searchMembers(word);
    }

    @Override
    public AdminMemberDTO getMemberDetail(String memberId) {
        return adminMapper.getMemberDetail(memberId);
    }

    @Override
    public void getMemberExcelFile(HttpServletResponse response) throws IOException {
        downExcelView.makeWorkbook(adminMapper.getAllMembers(), "회원 목록", response);
    }
}

