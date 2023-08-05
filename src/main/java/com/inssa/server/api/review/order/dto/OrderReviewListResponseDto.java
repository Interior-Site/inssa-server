package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.build_type.dto.BuildTypeListResponse;
import com.inssa.server.api.review.category.dto.CategoryListResponse;
import com.inssa.server.api.review.order.model.OrderReview;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Getter
public class OrderReviewListResponseDto {

    /**
     * 제목
     */
    private final String title;

    /**
     * 내용
     */
    private final String content;

    /**
     * 견적 금액
     */
    private final int amount;

    /**
     * 건물 유형
     */
    private final List<CategoryListResponse> categories;

    /**
     * 시공 유형
     */
    private final List<BuildTypeListResponse> buildTypes;

    public OrderReviewListResponseDto(OrderReview orderReview){
        this.title = orderReview.getTitle();
        this.content = orderReview.getContent();
        this.amount = orderReview.getAmount();
        this.categories = null;
        this.buildTypes = null;
    }

}
