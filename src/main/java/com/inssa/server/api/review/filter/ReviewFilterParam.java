package com.inssa.server.api.review.filter;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ReviewFilterParam {
    private final List<Long> buildTypes; // 필터: 건물 유형
    private final List<Long> categories; // 필터: 시공 유형
    private final String keyword; // 검색어
    private final String sortBy; // 정렬
}
