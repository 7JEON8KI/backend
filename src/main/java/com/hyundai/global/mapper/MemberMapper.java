package com.hyundai.global.mapper;

import com.hyundai.domain.login.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberMapper {

    void insertMemberAndInfo(Map<String, Object> map);
    Optional<Member> findMemberByEmail(String memberEmail);
    void updateRefreshToken(Map<String, Object> map);
    void deleteMember(String memberId);
    Member getMemberByEmail(String email);


    Member findMemberByMemberId(@Param("memberId")String memberId);
    Member findMemberByRefreshToken(@Param("refreshToken")String refreshToken);
    void saveMember(Member member);
    List<Member> getMemberList();
    boolean updateMember(Member entity);
    boolean updateDeletedMember(Member entity);
}
