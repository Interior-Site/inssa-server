package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.review.build_type.model.BuildType;
import com.inssa.server.api.review.category.model.Category;
import com.inssa.server.api.review.order.model.OrderReview;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderReviewResponseDto extends ReviewResponseDto {

    @Schema(description = "견적 후기 번호")
    private final Long no;

    @Schema(
            description = "견적 비용",
            example = "3000000",
            type = "integer",
            minimum = "0"
    )
    private final int amount;

    @Schema(description = "공감수")
    private final int likeCount;

    @Schema(description = "댓글수")
    private final int commentCount;

    public OrderReviewResponseDto(OrderReview review, Company company, List<BuildType> buildTypes, List<Category> categories) {
        super(review, company, buildTypes, categories);
        this.no = review.getNo();
        this.amount = review.getAmount();
        this.likeCount = review.getLikeCount();
        this.commentCount = review.getCommentCount();
    }
}
