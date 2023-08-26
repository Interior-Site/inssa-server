package com.inssa.server.api.review.comment.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@Data
public class ReviewCommentResponseDto {
    @Schema(description = "후기 댓글 번호")
    private final Long commentNo;

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

    public ReviewCommentResponseDto(Long commentNo) {
        this.commentNo = commentNo;
        this.content = null;
        this.user = null;
        this.createdDate = null;
        this.modifiedDate = null;
        this.like = null;
    }
}
