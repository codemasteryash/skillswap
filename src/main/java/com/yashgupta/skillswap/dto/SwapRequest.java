package com.yashgupta.skillswap.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwapRequest {
    private Long learnerId;
    private Long teacherId;
    private Double hours;
    private Long skillId;
}
