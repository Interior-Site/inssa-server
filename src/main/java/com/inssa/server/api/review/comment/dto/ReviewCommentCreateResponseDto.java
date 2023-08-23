package com.inssa.server.api.review.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class ReviewCommentCreateResponseDto {

    @Schema(
            description = "생성된 견적 후기 번호",
            type = "Long",
            example = "5"
    )
    private final Long commentNo;

    public ReviewCommentCreateResponseDto(Long no) {
        this.commentNo = no;
    }
}
