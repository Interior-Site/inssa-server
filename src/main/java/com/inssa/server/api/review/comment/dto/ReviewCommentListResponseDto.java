package com.inssa.server.api.review.comment.dto;

import com.inssa.server.api.review.comment.model.BuildReviewComment;
import com.inssa.server.api.review.comment.model.OrderReviewComment;
import com.inssa.server.api.user.dto.UserResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ReviewCommentListResponseDto {
    private final Long no;
    private final String content;
    private final boolean deleted;
    private final UserResponseDto user;
    private final Long reviewNo;
    private final List<ReviewCommentResponseDto> children;

    public ReviewCommentListResponseDto(OrderReviewComment reviewComment){
        this.reviewNo = reviewComment.getReviewNo();
        this.no = reviewComment.getNo();
        this.content = reviewComment.getContent();
        this.deleted = reviewComment.isDeleted();
        this.user = new UserResponseDto(reviewComment.getUser());
        this.children = reviewComment.getChildren().stream()
                .map(ReviewCommentResponseDto::new).toList();
    }

    public ReviewCommentListResponseDto(BuildReviewComment reviewComment){
        this.no = reviewComment.getNo();
        this.content = reviewComment.getContent();
        this.deleted = reviewComment.isDeleted();
        this.user = new UserResponseDto(reviewComment.getUser());
        this.reviewNo = reviewComment.getReviewNo();
        this.children = reviewComment.getChildren().stream()
                .map(ReviewCommentResponseDto::new).toList();
    }
}
