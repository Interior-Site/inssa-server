package com.inssa.server.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "사용자 정보 수정 API request")
public class UserChangeInfoRequestDto {
    @Schema(description = "아이디")
    private String userId;
    @Schema(description = "이메일", example = "inssa@naver.com")
    private String email;
    @Schema(description = "닉네임", example = "나무")
    private String nickname;
    @Schema(description = "핸드폰 번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "프로필 사진 경로")
    private String img;
}
