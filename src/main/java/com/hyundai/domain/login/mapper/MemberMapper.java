package com.hyundai.domain.login.mapper;

import com.hyundai.domain.login.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {

    void insertMember(Member entity);
    Member getMember(String id);
    List<Member> getMemberList();
    boolean updateMember(Member entity);
    boolean updateDeletedMember(Member entity);
    int deleteMember(String id);
    Member getMemberByEmail(String email);

    Member findMemberByEmail(@Param("email")String email);
    Member findMemberByMemberId(@Param("memberId")String memberId);
    Member findMemberByRefreshToken(@Param("refreshToken")String refreshToken);
    void saveMember(Member member);
}
