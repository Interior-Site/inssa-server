package com.inssa.server.api.review.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "견적 후기 수정 request")
@Getter
@NoArgsConstructor
public class OrderReviewUpdateRequestDto {

    @Schema(description = "견적 후기 번호")
    @NotNull(message = "견적 후기 번호가 누락되었습니다.")
    private Long no;

    @Schema(description = "견적 비용")
    @NotNull(message = "금액 정보가 누락되었습니다.")
    @PositiveOrZero(message = "금액은 0원 이상만 입력 가능합니다.")
    private int amount;

    @Schema(description = "제목", maxLength = 100)
    @NotBlank(message = "제목은 공백일 수 없습니다.")
    @Max(value = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title;

    @Schema(description = "내용")
    @NotBlank(message = "내용은 공백일 수 없습니다.")
    private String content;

    @Schema(description = "업체 번호")
    @NotNull(message = "업체 번호가 누락되었습니다.")
    private Long companyNo;

    @Schema(description = "건물 유형 번호 배열")
    private List<Long> buildTypes;

    @Schema(description = "시공 유형 번호 배열")
    private List<Long> categories;

    // TODO: 이미지
}
