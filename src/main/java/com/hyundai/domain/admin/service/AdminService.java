package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminMemberDTO;
import com.hyundai.domain.admin.dto.AdminMemberParamDTO;
import com.hyundai.domain.login.entity.Member;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AdminService {
    List<AdminMemberDTO> getMemberList(AdminMemberParamDTO paramDTO);

    List<AdminMemberDTO> getMemberListByPage(AdminMemberParamDTO paramDTO, Long pageNum);

    String modifyMember(AdminMemberDTO member);

    String deleteMember(AdminMemberDTO member);

    String changeMemberAuthorization(AdminMemberDTO member);


    void getMemberExcelFile(HttpServletResponse response) throws IOException;

    List<AdminMemberDTO> searchMembers(String word);

    AdminMemberDTO getMemberDetail(String memberId);


}
