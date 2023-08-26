package com.inssa.server.api.review.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewCommentUpdateRequestDto {

    @Schema(description = "댓글 PK")
    @NotNull(message = "댓글 PK가 올바르지 않습니다.")
    private Long commentNo;

    @Schema(description = "댓글 내용", type = "String")
    @NotBlank(message = "내용은 공백일 수 없습니다.")
    @NotNull(message = "내용은 null일 수 없습니다.")
    @NotEmpty(message = "내용은 빈 문자열일 수 없습니다.")
    private String content;
}
