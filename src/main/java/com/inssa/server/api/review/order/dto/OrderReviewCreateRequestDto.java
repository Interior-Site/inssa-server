package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.order.model.OrderReview;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Schema(description = "견적 후기 요청") // TODO: 항목별 스키마 입력
@NoArgsConstructor
@Getter
public class OrderReviewCreateRequestDto {

    @NotNull(message = "금액 정보가 누락되었습니다.")
    @PositiveOrZero(message = "금액은 0 이상이어야 합니다.")
    private int amount;

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    @Max(value = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    private String content;

    @NotNull(message = "업체 번호가 누락되었습니다.")
    private Long companyNo;

    @NotNull(message = "건물 유형이 누락되었습니다.")
    private List<@Valid Long> buildTypes;

    @NotNull(message = "시공 유형이 누락되었습니다.")
    private List<@Valid Long> categories;

    // TODO: 이미지

    public OrderReview toEntity(Long userNo, Long companyNo) {
        return OrderReview.builder()
                .amount(this.amount)
                .title(this.title)
                .content(this.content)
                .userNo(userNo)
                .companyNo(companyNo)
                .build();
    }
}
