package com.hyundai.login.service;

import com.hyundai.login.domain.Member;
import com.hyundai.login.mapper.MemberMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public Member getMemberByMemberId(String memberId) {
        return memberMapper.findMemberByMemberId(memberId);
    }
}