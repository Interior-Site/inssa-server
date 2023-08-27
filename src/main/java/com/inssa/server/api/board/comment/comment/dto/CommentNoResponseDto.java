package com.inssa.server.api.board.comment.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentNoResponseDto {
    @Schema(description = "댓글 번호")
    private final Long commentNo;
}
