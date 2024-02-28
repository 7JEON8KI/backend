package com.hyundai.global.mapper;

import com.hyundai.domain.login.dto.MemberInfoResponseDto;
import com.hyundai.domain.login.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Optional;

public interface MemberMapper {

    void insertMemberAndInfo(Map<String, Object> map);
    Optional<Member> findMemberByEmail(String memberEmail);
    void updateRefreshToken(Map<String, Object> map);
    void deleteMember(String memberId);
    Optional<Member> findMemberByMemberId(@Param("memberId")String memberId);
    void registerStore(Map<String, Object> map);
    Optional<MemberInfoResponseDto> getMemberByMemberId(String memberId);
    void updateMember(Map<String, Object> map);
    void updateMemberInfo(Map<String, Object> map);
}
