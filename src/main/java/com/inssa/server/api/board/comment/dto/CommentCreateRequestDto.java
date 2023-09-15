package com.inssa.server.api.board.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentCreateRequestDto {

    @Schema(description = "게시글 번호")
    @NotNull(message = "게시글 번호는 null일 수 없습니다.")
    @Positive(message = "게시글 번호는 음수일 수 없습니다.")
    private Long postNo;

    @Schema(description = "댓글 내용")
    @NotBlank(message = "내용은 공백일 수 없습니다.")
    @NotNull(message = "내용은 null일 수 없습니다.")
    @NotEmpty(message = "내용은 빈 문자열일 수 없습니다.")
    private String content;

    @Schema(description = "상위 댓글")
    private Long parentNo;
}
