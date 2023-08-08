package com.inssa.server.api.review.category.dto;

import com.inssa.server.api.review.category.model.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryListResponse {
    private final Long no;
    private final String name;

    public CategoryListResponse(Category category) {
        this.no = category.getNo();
        this.name = category.getName();
    }
}
