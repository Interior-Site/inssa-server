package com.inssa.server.api.review.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewCommentRequestDto {

    private final Long reviewNo;
    private Long commentNo;
    private Long parentNo;
    private String content;
    private final Long userNo;


    @Builder(builderMethodName = "createBuilder", builderClassName = "createBuilder")
    public ReviewCommentRequestDto(Long reviewNo, String content, Long parentNo, Long userNo) {
        this.reviewNo = reviewNo;
        this.parentNo = parentNo;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "updateBuilder", builderClassName = "updateBuilder")
    public ReviewCommentRequestDto(Long reviewNo, Long commentNo, String content, Long userNo) {
        this.reviewNo = reviewNo;
        this.commentNo = commentNo;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "deleteBuilder", builderClassName = "deleteBuilder")
    public ReviewCommentRequestDto(Long reviewNo, Long commentNo, Long userNo) {
        this.reviewNo = reviewNo;
        this.commentNo = commentNo;
        this.userNo = userNo;
    }

}
