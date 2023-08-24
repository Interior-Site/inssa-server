package com.inssa.server.api.review.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inssa.server.api.company.dto.CompanyResponseDto;
import com.inssa.server.api.review.comment.dto.ReviewUserResponseDto;
import com.inssa.server.share.board.BoardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class ReviewResponseDto {

    @Schema(description = "후기 번호")
    private final Long no;

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
    private final ReviewUserResponseDto user;

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
}
