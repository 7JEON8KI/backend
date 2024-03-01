package com.hyundai.global.mapper;

import java.util.Map;

/**
 * @author : 강은구
 * @fileName : LogMapper
 * @description :
 * @since : 03/01/2024
 */
public interface LogMapper {
    void insertLog(Map<String, Object> logs);
}
