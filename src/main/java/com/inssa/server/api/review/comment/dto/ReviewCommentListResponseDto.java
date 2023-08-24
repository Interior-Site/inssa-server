package com.inssa.server.api.review.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class ReviewCommentListResponseDto extends ReviewCommentResponseDto{

    @Schema(description = "후기 답댓글")
    private final List<ReviewCommentResponseDto> children;

    public ReviewCommentListResponseDto(Long no, String content, ReviewUserResponseDto user, LocalDateTime createdDate, LocalDateTime modifiedDate, ReviewCommentLikeResponseDto likes, List<ReviewCommentResponseDto> children) {
        super(no, content, user, createdDate, modifiedDate, likes);
        this.children = children;
    }
}
