package com.inssa.server.api.review.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReviewLikeDeleteResponseDto {
    private final Long userNo;
    private final Long reviewNo;
    private final boolean liked;
}
