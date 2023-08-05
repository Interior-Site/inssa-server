package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.review.build_type.model.BuildType;
import com.inssa.server.api.review.category.model.Category;
import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.share.board.BoardStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderReviewResponseDto {

    private Long no;
    private Long userNo;
    private int amount;
    private String title;
    private String content;
    private BoardStatus status;
    private int viewCount;

    private CompanyResponseDto company;

    private List<BuildTypeResponseDto> buildTypes;
    private List<CategoryResponseDto> categories;

    public OrderReviewResponseDto(OrderReview orderReview, Company company, List<BuildType> buildTypes, List<Category> categories) {
        this.no = orderReview.getNo();
        this.userNo = orderReview.getUserNo();
        this.amount = orderReview.getAmount();
        this.title = orderReview.getTitle();
        this.content = orderReview.getContent();
        this.status = orderReview.getStatus();
        this.viewCount = orderReview.getViewCount();
        this.company = new CompanyResponseDto(company);
        this.buildTypes = buildTypes.stream().map(BuildTypeResponseDto::new).toList();
        this.categories = categories.stream().map(CategoryResponseDto::new).toList();
    }
}
