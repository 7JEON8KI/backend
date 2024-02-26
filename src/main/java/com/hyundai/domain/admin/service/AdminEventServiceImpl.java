package com.hyundai.domain.admin.service;

import com.hyundai.domain.admin.dto.AdminEventDTO;
import com.hyundai.domain.login.security.CustomMemberDetails;
import com.hyundai.global.exception.GlobalErrorCode;
import com.hyundai.global.exception.GlobalException;
import com.hyundai.global.mapper.AdminEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : 강은구
 * @fileName : AdminEventServiceImpl
 * @description :
 * @since : 02/24/2024
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService{

    private final AdminEventMapper adminEventMapper;

    @Override
    public List<AdminEventDTO> getEventList() {

        String loginMemberId = ((CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberId();
        List<AdminEventDTO> results = adminEventMapper.getEventList(loginMemberId);
        return results;
    }

    @Override
    public String insertEvent(AdminEventDTO paramDTO) {
        int result = adminEventMapper.insertEvent(paramDTO);
        if(result == 1){
            return " ";
        }
        else {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public String modifyEvent(AdminEventDTO paramDTO) {
        int result = adminEventMapper.modifyEvent(paramDTO);
        if (result == 1) {
            return " ";
        } else {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }

    @Override
    public String deleteEvent(AdminEventDTO paramDTO) {
        int result = adminEventMapper.deleteEvent(paramDTO);
        if (result == 1) {
            return " ";
        } else {
            throw new GlobalException(GlobalErrorCode.NON_CLEAR_REASON);
        }
    }
}