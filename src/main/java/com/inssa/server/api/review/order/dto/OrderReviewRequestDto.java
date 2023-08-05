package com.inssa.server.api.review.order.dto;

import com.inssa.server.share.board.BoardStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class OrderReviewRequestDto {

    private Long no;
    private int amount;
    private String title;
    private String content;
    private BoardStatus status;
    private int viewCount;
    private Long userNo;
    private Long companyNo;
    private List<Long> buildTypes;
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
