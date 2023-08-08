package com.inssa.server.api.review.order.dto;

import com.inssa.server.share.board.BoardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "견적 후기 Request")
@NoArgsConstructor
@Getter
public class OrderReviewRequestDto {

    @Schema(description = "견적 후기 번호")
    private Long no;

    @Schema(
            description = "견적 비용",
            example = "3000000",
            type = "integer",
            minimum = "0"
    )
    private int amount;

    @Schema(
            description = "견적 후기 제목",
            maxLength = 100,
            type = "String"
    )
    private String title;

    @Schema(description = "견적 후기 내용")
    private String content;

    @Schema(
            description = "견적 후기 상태",
            type = "String",
            allowableValues = {"VISIBLE", "DELETED"}
    )
    private BoardStatus status;

    @Schema(description = "조회수")
    private int viewCount;

    @Schema(description = "작성자 번호")
    private Long userNo;

    @Schema(description = "업체 번호")
    private Long companyNo;

    @Schema(
            description = "건물 유형 번호 배열",
            type = "List",
            subTypes = Long.class
    )
    private List<Long> buildTypes;

    @Schema(
            description = "시공 유형 번호 배열",
            type = "List",
            subTypes = Long.class
    )
    private List<Long> categories;

    @Builder(builderMethodName = "updateBuilder")
    public OrderReviewRequestDto(
            Long no,
            int amount,
            String title,
            String content,
            Long companyNo,
            List<Long> buildTypes,
            List<Long> categories,
            Long userNo
    ) {
        this.no = no;
        this.userNo = userNo;
        this.amount = amount;
        this.title = title;
        this.content = content;
        this.companyNo = companyNo;
        this.buildTypes = buildTypes;
        this.categories = categories;
    }

    @Builder(builderMethodName = "deleteBuilder")
    public OrderReviewRequestDto(Long no, Long userNo){
        this.no = no;
        this.userNo = userNo;
    }
}
