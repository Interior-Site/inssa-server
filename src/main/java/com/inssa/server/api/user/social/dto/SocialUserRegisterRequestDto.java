package com.inssa.server.api.user.social.dto;

import com.inssa.server.api.user.social.model.SocialType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "소셜 사용자 회원가입 API request")
public class SocialUserRegisterRequestDto {
    @Schema(description = "이메일", example = "inssa@naver.com")
    private String email;
    @Schema(description = "닉네임", example = "나무")
    private String nickname;
    @Schema(description = "소셜 로그인 타입", example = "google")
    private SocialType socialType;
}
