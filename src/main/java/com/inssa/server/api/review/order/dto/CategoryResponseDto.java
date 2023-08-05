package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.category.model.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private Long no;
    private String name;
    public CategoryResponseDto(Category category) {
        this.no = category.getNo();
        this.name = category.getName();
    }

}
