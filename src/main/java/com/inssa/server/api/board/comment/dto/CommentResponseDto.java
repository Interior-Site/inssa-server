package com.inssa.server.api.board.comment.dto;


import com.inssa.server.api.board.commentlike.dto.CommentLikeResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CommentResponseDto {
    @Schema(description = "댓글 번호")
    private final Long commentNo;

    @Schema(description = "댓글 내용")
    private final String content;

    @Schema(description = "댓글 작성자")
    private final CommentUserResponseDto user;

    @Schema(description = "댓글 작성일시")
    private final LocalDateTime createdDate;

    @Schema(description = "댓글 수정일시")
    private final LocalDateTime modifiedDate;

    @Schema(description = "댓글 공감")
    private final CommentLikeResponseDto like;
}
