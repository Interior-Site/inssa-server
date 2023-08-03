package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.order.model.OrderReview;


public class OrderReviewListResponseDto {

    private final String title;
    private final String content;
    private final int amount;

//    private final List<CategoryListResponse> categories;
//    private final List<BuildTypeListResponse> buildTypes;

    public OrderReviewListResponseDto(OrderReview orderReview){
        this.title = orderReview.getTitle();
        this.content = orderReview.getContent();
        this.amount = orderReview.getAmount();
//        this.categories = null;
//        this.buildTypes = null;
    }

}
