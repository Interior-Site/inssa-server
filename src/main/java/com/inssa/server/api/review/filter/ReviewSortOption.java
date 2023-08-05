package com.inssa.server.api.review.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewSortOption {
    LATEST("latest"), // 최신순
    VIEWS("views"), // 조회순
    LIKES("likes"), // 공감순
    COMMENTS("comments"), // 댓글순
    AMOUNT_LOW_TO_HIGH("amountLowToHigh"), // 가격 낮은순
    AMOUNT_HIGH_TO_LOW("amountHighToLow") // 가격 높은순
    ;
    private final String value;

    public static ReviewSortOption fromValue(String value) {
        for (ReviewSortOption option : values()) {
            if (option.value.equalsIgnoreCase(value)) {
                return option;
            }
        }
        return LATEST; // Default value
    }
}
