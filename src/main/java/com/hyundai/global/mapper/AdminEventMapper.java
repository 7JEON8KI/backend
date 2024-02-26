package com.hyundai.global.mapper;

import com.hyundai.domain.admin.dto.AdminEventDTO;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : AdminEventMapper
 * @description :
 * @since : 02/24/2024
 */
public interface AdminEventMapper {

    List<AdminEventDTO> getEventList(String memberId);

    int insertEvent(AdminEventDTO paramDTO);

    int modifyEvent(AdminEventDTO paramDTO);

    int deleteEvent(AdminEventDTO paramDTO);
}
