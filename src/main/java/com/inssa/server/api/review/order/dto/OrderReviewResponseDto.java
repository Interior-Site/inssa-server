package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.review.comment.dto.ReviewUserResponseDto;
import com.inssa.server.api.review.like.dto.ReviewLikeResponseDto;
import com.inssa.server.share.board.BoardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class OrderReviewResponseDto extends ReviewResponseDto {

    @Schema(
            description = "견적 비용",
            example = "3000000",
            type = "integer",
            minimum = "0"
    )
    private final int amount;

    @Schema(description = "공감")
    private final ReviewLikeResponseDto like;

    @Schema(description = "댓글수")
    private final int commentCount;

    public OrderReviewResponseDto(Long no, String title, String content, BoardStatus status, int viewCount, ReviewUserResponseDto user, CompanyResponseDto company, List<BuildTypeResponseDto> buildTypes, List<CategoryResponseDto> categories, LocalDateTime createdDate, LocalDateTime modifiedDate, int amount, ReviewLikeResponseDto like, int commentCount) {
        super(no, title, content, status, viewCount, user, company, buildTypes, categories, createdDate, modifiedDate);
        this.amount = amount;
        this.like = like;
        this.commentCount = commentCount;
    }
}
