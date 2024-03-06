package com.hyundai.global.mapper;

import com.hyundai.domain.admin.dto.AdminManagerDTO;
import com.hyundai.domain.admin.dto.AdminMemberDTO;

import java.util.List;

public interface AdminMemberMapper {
    List<AdminMemberDTO> getAllMembers();

    void modifyMember(AdminMemberDTO memberDTO);

    void deleteMember(AdminMemberDTO memberDTO);

    void grantAuthorization(String memberId);

    List<AdminMemberDTO> searchMembers(String word);

    List<AdminManagerDTO> getAllManagers();

}
