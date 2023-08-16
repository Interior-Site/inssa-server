package com.inssa.server.api.review.like.dto;

import com.inssa.server.api.review.like.model.BuildReviewLike;
import com.inssa.server.api.review.like.model.OrderReviewLike;
import lombok.Getter;


@Getter
public class ReviewLikeResponseDto {
    private final Long userNo;
    private final Long reviewNo;
    private final boolean liked;

    public ReviewLikeResponseDto(OrderReviewLike orderReviewLike, boolean liked) {
        this.userNo = orderReviewLike.getUser().getNo();
        this.reviewNo = orderReviewLike.getOrderReview().getNo();
        this.liked = liked;
    }

    public ReviewLikeResponseDto(BuildReviewLike buildReviewLike, boolean liked) {
        this.userNo = buildReviewLike.getUser().getNo();
        this.reviewNo = buildReviewLike.getBuildReview().getNo();
        this.liked = liked;
    }
}
