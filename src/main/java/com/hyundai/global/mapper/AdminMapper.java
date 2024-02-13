package com.hyundai.global.mapper;

import com.hyundai.domain.admin.dto.AdminMember;
import com.hyundai.domain.login.entity.Member;
import com.hyundai.domain.utils.paging.Criteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    List<AdminMember> getListByStandard(Criteria cri, @Param("standard")String standard);
    String test();
}
