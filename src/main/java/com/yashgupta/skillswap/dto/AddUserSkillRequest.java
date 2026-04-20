package com.yashgupta.skillswap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserSkillRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long skillId;

    @NotBlank
    private String proficiencyLevel;
}