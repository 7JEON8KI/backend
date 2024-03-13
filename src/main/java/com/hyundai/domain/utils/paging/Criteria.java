package com.hyundai.domain.utils.paging;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * @author : 강은구
 * @since : 02/19/2024
 */
@Getter
@Setter
@ToString
public class Criteria {
    private Long pageNum;
    private Long amount;

    public Criteria(){
        this(1L,10L);
    }

    public Criteria(Long pageNum, Long amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }
}
