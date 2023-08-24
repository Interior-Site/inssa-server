package com.inssa.server.api.review.comment.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ReviewCommentResponseDto {
    @Schema(description = "후기 댓글 번호")
    private final Long no;

    @Schema(description = "후기 댓글 내용")
    private final String content;

    @Schema(description = "후기 댓글 작성자")
    private final ReviewUserResponseDto user;

    @Schema(description = "후기 댓글 작성일시")
    private final LocalDateTime createdDate;

    @Schema(description = "후기 댓글 수정일시")
    private final LocalDateTime modifiedDate;

    @Schema(description = "후기 댓글 공감")
    private final ReviewCommentLikeResponseDto like;
}
