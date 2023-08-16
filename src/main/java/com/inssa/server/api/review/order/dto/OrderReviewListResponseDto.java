package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.build_type.dto.BuildTypeListResponse;
import com.inssa.server.api.review.category.dto.CategoryListResponse;
import com.inssa.server.api.review.order.model.OrderReview;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Getter
public class OrderReviewListResponseDto {

    @Schema(description = "견적 후기 제목")
    private final String title;

    @Schema(description = "견적 후기 내용")
    private final String content;

    @Schema(description = "견적 비용")
    private final int amount;

    @Schema(description = "공감수")
    private final int likeCount;

    @Schema(
            description = "시공 유형 객체 배열",
            type = "List",
            subTypes = CategoryListResponse.class
    )
    private final List<CategoryListResponse> categories;

    @Schema(
            description = "건물 유형 객체 배열",
            type = "List",
            subTypes = BuildTypeListResponse.class
    )
    private final List<BuildTypeListResponse> buildTypes;

    public OrderReviewListResponseDto(OrderReview orderReview){
        this.title = orderReview.getTitle();
        this.content = orderReview.getContent();
        this.amount = orderReview.getAmount();
        this.categories = orderReview.getCategories().stream().map(CategoryListResponse::new).toList();
        this.buildTypes = orderReview.getBuildTypes().stream().map(BuildTypeListResponse::new).toList();
        this.likeCount = orderReview.getLikeCount();
    }

}
