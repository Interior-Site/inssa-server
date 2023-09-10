package com.inssa.server.api.board.comment.comment.dto;

import com.inssa.server.api.image.dto.ImageResponseDto;
import com.inssa.server.api.user.model.UserRole;
import com.inssa.server.share.user.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentUserResponseDto {

    @Schema(description = "작성자 PK")
    private final Long no;

    @Schema(description = "작성자 닉네임")
    private final String nickname;

    @Schema(description = "작성자 상태")
    private final UserStatus status;

    @Schema(description = "작성자 프로필")
    private final ImageResponseDto profile;

    @Schema(description = "작성자 등급")
    private final UserRole role;
}
