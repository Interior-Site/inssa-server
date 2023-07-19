package com.inssa.server.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "사용자 회원가입 API request")
public class UserRegisterRequestDto {
    @Schema(description = "이메일", example = "inssa@naver.com")
    private String email;
    @Schema(description = "패스워드")
    private String password;
    @Schema(description = "닉네임", example = "나무")
    private String nickname;
    @Schema(description = "프로필 사진 경로")
    private String profile;
    @Schema(description = "회원 타입", example = "admin/user")
    private String role;
}
