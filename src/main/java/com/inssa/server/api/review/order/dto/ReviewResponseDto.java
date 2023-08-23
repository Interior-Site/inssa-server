package com.inssa.server.api.review.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.review.build_type.model.BuildType;
import com.inssa.server.api.review.category.model.Category;
import com.inssa.server.api.review.order.model.Review;
import com.inssa.server.api.user.dto.UserResponseDto;
import com.inssa.server.share.board.BoardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponseDto {

    @Schema(
            description = "후기 제목",
            maxLength = 100,
            type = "String"
    )
    private final String title;

    @Schema(description = "후기 내용")
    private final String content;

    @Schema(
            description = "후기 상태",
            type = "String",
            allowableValues = {"VISIBLE", "DELETED"}
    )
    private final BoardStatus status;

    @Schema(description = "조회수")
    private final int viewCount;

    @Schema(description = "작성자")
    private final UserResponseDto user;

    @Schema(
            description = "업체 정보 객체",
            type = "CompanyResponseDto"
    )
    private final CompanyResponseDto company;

    @Schema(
            description = "건물 유형 객체 배열",
            type = "List",
            subTypes = BuildTypeResponseDto.class
    )
    private final List<BuildTypeResponseDto> buildTypes;

    @Schema(
            description = "시공 유형 객체 배열",
            type = "List",
            subTypes = CategoryResponseDto.class
    )
    private final List<CategoryResponseDto> categories;

    @Schema(
            description = "생성일시(yyyy-MM-dd hh:mm:ss)",
            type = "LocalDateTime"
    )
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime createdDate;

    @Schema(
            description = "수정일시(yyyy-MM-dd hh:mm:ss)",
            type = "LocalDateTime"
    )
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime modifiedDate;

    public ReviewResponseDto(Review review, Company company, List<BuildType> buildTypes, List<Category> categories) {
        this.user = new UserResponseDto(review.getUser());
        this.title = review.getTitle();
        this.content = review.getContent();
        this.status = review.getStatus();
        this.viewCount = review.getViewCount();
        this.company = new CompanyResponseDto(company);
        this.buildTypes = buildTypes.stream().map(BuildTypeResponseDto::new).toList();
        this.categories = categories.stream().map(CategoryResponseDto::new).toList();
        this.createdDate = review.getCreatedDate();
        this.modifiedDate = review.getModifiedDate();
    }
}
