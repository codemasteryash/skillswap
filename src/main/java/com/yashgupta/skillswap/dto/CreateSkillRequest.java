package com.yashgupta.skillswap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSkillRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    @Positive
    private Double pricePerHour;
}