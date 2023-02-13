package com.inssa.server.api.user.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String email;
    private String password;
}
