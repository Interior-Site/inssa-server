package com.inssa.server.api.user.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "사용자 정보 수정 API request")
public class UserChangeInfoRequestDto {
    @Schema(description = "닉네임", example = "나무")
    private String nickname;
}
