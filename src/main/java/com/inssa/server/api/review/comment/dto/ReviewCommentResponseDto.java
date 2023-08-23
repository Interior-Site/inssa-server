package com.inssa.server.api.review.comment.dto;


import com.inssa.server.api.review.comment.model.BuildReviewComment;
import com.inssa.server.api.review.comment.model.OrderReviewComment;
import com.inssa.server.api.user.dto.UserResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReviewCommentResponseDto {
    private final Long no;
    private final String content;
    private final boolean deleted;
    private final UserResponseDto user;
    private final Long reviewNo;

    public ReviewCommentResponseDto(OrderReviewComment reviewComment){
        this.no = reviewComment.getNo();
        this.content = reviewComment.getContent();
        this.deleted = reviewComment.isDeleted();
        this.user = new UserResponseDto(reviewComment.getUser());
        this.reviewNo = reviewComment.getReviewNo();
    }

    public ReviewCommentResponseDto(BuildReviewComment reviewComment){
        this.no = reviewComment.getNo();
        this.content = reviewComment.getContent();
        this.deleted = reviewComment.isDeleted();
        this.user = new UserResponseDto(reviewComment.getUser());
        this.reviewNo = reviewComment.getReviewNo();
    }
}
