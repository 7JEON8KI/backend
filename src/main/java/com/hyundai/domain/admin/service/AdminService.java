package com.hyundai.domain.admin.service;

import com.hyundai.domain.login.entity.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface AdminService {
    Map<String, Object> getMemberList();

    public Map<String, Object> getMemberListByPage(String standard, Long pageNum);

    void modifyMember(Member member);
    void deleteMember(Member member);


}
