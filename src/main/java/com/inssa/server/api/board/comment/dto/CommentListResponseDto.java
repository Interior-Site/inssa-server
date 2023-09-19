package com.inssa.server.api.board.comment.dto;

import com.inssa.server.api.board.commentlike.dto.CommentLikeResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class CommentListResponseDto extends CommentResponseDto {

    @Schema(description = "답댓글")
    private final List<CommentResponseDto> children;

    public CommentListResponseDto(Long no, String content, CommentUserResponseDto user, LocalDateTime createdDate, LocalDateTime modifiedDate, CommentLikeResponseDto likes, List<CommentResponseDto> children) {
        super(no, content, user, createdDate, modifiedDate, likes);
        this.children = children;
    }
}
