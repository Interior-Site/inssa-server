package com.inssa.server.api.review.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Schema(description = "후기 - 필터 쿼리 파라미터")
@RequiredArgsConstructor
@Getter
public class ReviewFilterParam {

    @Schema(
            description = "건물 유형 번호 배열 (콤마로 구분, 띄어쓰기 불가)",
            type = "List",
            subTypes = Long.class,
            example = "1,2,3"
    )
    @Min(value = 1, message = "건물 유형 값은 1 이상이어야 합니다.")
    private final List<@Valid Long> buildTypes; // 필터: 건물 유형

    @Schema(
            description = "시공 유형 번호 배열 (콤마로 구분, 띄어쓰기 불가)",
            type = "List",
            subTypes = Long.class,
            example = "3,4"
    )
    @Min(value = 1, message = "시공 유형 값은 1 이상이어야 합니다.")
    private final List<@Valid Long> categories; // 필터: 시공 유형

    @Schema(
            description = "검색어 (공백 제외 2자 이상 입력 가능)",
            type = "String",
            minLength = 2
    )
    @Size(min = 2, message = "검색어는 2자 이상 입력해야 합니다.")
    private final String keyword; // 검색어

    @Schema(
            description = "정렬 기준",
            type = "String",
            allowableValues = {"latest", "views", "likes", "comments", "amountLowToHigh", "amountHighToLow"}
    )
    private final String sort; // 정렬
}
