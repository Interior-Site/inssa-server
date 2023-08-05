package com.inssa.server.api.review.order.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderReviewUpdateRequestDto {

    @NotNull(message = "견적 후기 번호가 누락되었습니다.")
    private Long no;

    @NotNull(message = "금액 정보가 누락되었습니다.")
    @PositiveOrZero(message = "금액은 0원 이상만 입력 가능합니다.")
    private int amount;

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    @Max(value = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    private String content;

    @NotNull(message = "업체 번호가 누락되었습니다.")
    private Long companyNo;

    @ElementCollection
    private List<Long> buildTypes;

    @ElementCollection
    private List<Long> categories;

    // TODO: 이미지
}
