package com.hyundai.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author : 강은구
 * @fileName : AdminEventDTO
 * @description :
 * @since : 02/24/2024
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminEventDTO {
    private Long eventId;
    private String eventTitle;
    private String eventDetail;
    private String eventImageUrl;

    private LocalDateTime eventStartDay;

    private LocalDateTime eventEndDay;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}
