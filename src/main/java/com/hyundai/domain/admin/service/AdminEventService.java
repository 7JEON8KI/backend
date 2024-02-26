package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminEventDTO;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : AdminEventService
 * @description :
 * @since : 02/24/2024
 */
public interface AdminEventService {
    List<AdminEventDTO> getEventList();

    String insertEvent(AdminEventDTO paramDTO);

    String modifyEvent(AdminEventDTO paramDTO);

    String deleteEvent(AdminEventDTO paramDTO);
}
