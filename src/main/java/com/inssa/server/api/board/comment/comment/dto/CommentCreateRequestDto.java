package com.inssa.server.api.board.comment.comment.dto;

import com.inssa.server.common.annotation.EnumValid;
import com.inssa.server.share.bookmark.BookmarkType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateRequestDto {

    @Schema(description = "게시글 유형")
    @NotNull(message = "게시글 유형은 null일 수 없습니다.")
    @EnumValid(enumClass = BookmarkType.class, message = "게시글 유형이 올바르지 않습니다.")
    private String type;

    @Schema(description = "댓글 내용")
    @NotBlank(message = "내용은 공백일 수 없습니다.")
    @NotNull(message = "내용은 null일 수 없습니다.")
    @NotEmpty(message = "내용은 빈 문자열일 수 없습니다.")
    private String content;

    @Schema(description = "상위 댓글")
    private Long parentNo;
}
