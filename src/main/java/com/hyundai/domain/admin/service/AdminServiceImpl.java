package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.Enum.Standard;
import com.hyundai.domain.login.entity.Member;
import com.hyundai.domain.utils.paging.Criteria;
import com.hyundai.global.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminMapper adminMapper;


    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getMemberList() {
        System.out.println(Standard.ID.getStandard());
        Criteria cri = new Criteria();
        List<Member> members = adminMapper.getListByStandard(cri, Standard.ID.getStandard());
        Map<String, Object> result = new HashMap<>();
        result.put("members", members);
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getMemberListByPage(String standard, Long pageNum) {
        Criteria cri = new Criteria();
        cri.setPageNum(pageNum);
        Standard stan = Standard.valueOf(standard);
        List<Member> members = adminMapper.getListByStandard(cri, stan.getStandard());
        Map<String, Object> result = new HashMap<>();
        result.put("members", members);
        return result;
    }
    @Override
    public void modifyMember(Member member) {

    }

    @Override
    public void deleteMember(Member member) {

    }
}
