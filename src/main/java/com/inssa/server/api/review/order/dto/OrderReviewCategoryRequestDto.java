package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.category.model.Category;
import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.api.review.order.model.OrderReviewCategory;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderReviewCategoryRequestDto {

    @NotNull(message = "견적 후기 정보가 누락되었습니다.")
    @ElementCollection
    private OrderReview orderReview;

    @NotNull(message = "시공 유형 정보가 누락되었습니다.")
    @ElementCollection
    private Category category;

    public OrderReviewCategory toEntity() {
        return OrderReviewCategory.builder()
                .orderReview(orderReview)
                .category(category)
                .build();
    }
}
