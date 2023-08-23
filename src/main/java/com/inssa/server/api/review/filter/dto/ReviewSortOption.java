package com.inssa.server.api.review.filter.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

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

    public static Optional<ReviewSortOption> fromValue(String value) {
        // default
        if (Objects.isNull(value)) {
            return Optional.of(LATEST);
        }
        for (ReviewSortOption option : values()) {
            if (option.value.equalsIgnoreCase(value)) {
                return Optional.of(option);
            }
        }
        return Optional.empty(); // null
    }
}
