package com.inssa.server.api.review.filter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ReviewFilterParam {

    @Min(value = 1, message = "건물 유형 값은 1 이상이어야 합니다.")
    private final List<@Valid Long> buildTypes; // 필터: 건물 유형

    @Min(value = 1, message = "시공 유형 값은 1 이상이어야 합니다.")
    private final List<@Valid Long> categories; // 필터: 시공 유형

    @NotBlank(message = "검색어가 없습니다.")
    @Size(min = 2, message = "검색어는 2자 이상 입력해야 합니다.")
    private final String keyword; // 검색어

    @NotBlank(message = "정렬 기준이 없습니다.")
    private final String sortBy; // 정렬
}
