package com.inssa.server.api.review.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class ReviewLikeResponseDto {
    private final boolean liked;
    private final int likeCount;
}
