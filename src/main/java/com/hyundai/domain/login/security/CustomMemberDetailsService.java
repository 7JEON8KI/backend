package com.hyundai.domain.login.security;

import com.hyundai.domain.login.entity.Member;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * fileName      : CustomMemberDetailsService
 * author        : 변형준
 * since         : 2/20/24
 * 내용 : 시큐리티의 UserDetailsService 구현 - 사용자 DB에 존재하는 지 검증용
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        Member member = memberMapper.findMemberByEmail(memberEmail)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_NOT_FOUND));
        log.debug("loadUserByUsername() :: " + member.getMemberEmail());
        return CustomMemberDetails.create(member);
    }

    public UserDetails loadUserByMemberId(String memberId) {
        log.debug("loadUserByMemberId() :: " + memberId);
        Member member = memberMapper.findMemberByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_NOT_FOUND));
        log.debug("loadUserByMemberId() :: " + member.getMemberId());
        return CustomMemberDetails.create(member);
    }
}
