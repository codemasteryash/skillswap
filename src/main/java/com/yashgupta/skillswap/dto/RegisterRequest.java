package com.yashgupta.skillswap.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @Size(max = 80)
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank @Size(min = 8, max = 120)
    private String password;
}
