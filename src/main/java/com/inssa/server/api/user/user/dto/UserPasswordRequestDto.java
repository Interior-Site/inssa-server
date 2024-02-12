package com.inssa.server.api.user.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "사용자 비밀번호 변경 API request")
public class UserPasswordRequestDto {
    @Schema(description = "패스워드")
    private String password;
}
