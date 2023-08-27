package com.inssa.server.api.board.comment.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CommentLikeResponseDto {

    @Schema(description = "댓글 공감 여부")
    private final boolean liked;

    @Schema(description = "댓글 공감수")
    private final int count;
}
