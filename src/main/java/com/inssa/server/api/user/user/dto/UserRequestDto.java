package com.inssa.server.api.user.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "사용자 로그인 API request")
public class UserRequestDto {
    @Schema(description = "이메일", example = "inssa@naver.com")
    private String email;
    @Schema(description = "패스워드")
    private String password;
}
