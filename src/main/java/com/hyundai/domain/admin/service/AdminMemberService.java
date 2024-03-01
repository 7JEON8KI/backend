package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminManagerDTO;
import com.hyundai.domain.admin.dto.AdminMemberDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AdminMemberService {
    List<AdminMemberDTO> getMemberList();

    String modifyMember(AdminMemberDTO member);

    String deleteMember(AdminMemberDTO member);

    String changeMemberAuthorization(AdminManagerDTO member);

    void getMemberExcelFile(HttpServletResponse response) throws IOException;

    List<AdminMemberDTO> searchMembers(String word);

    List<AdminManagerDTO> getManagerList();

}
