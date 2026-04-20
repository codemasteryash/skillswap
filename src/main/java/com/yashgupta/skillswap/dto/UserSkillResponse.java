package com.yashgupta.skillswap.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSkillResponse {
    private Long id;

    private Long userId;
    private String username;

    private Long skillId;
    private String skillName;
    private String category;
    private Double pricePerHour;

    private String proficiencyLevel;
}