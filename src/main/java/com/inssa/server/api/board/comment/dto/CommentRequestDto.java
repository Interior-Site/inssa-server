package com.inssa.server.api.board.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long postNo;
    private Long commentNo;
    private Long parentNo;
    private final Long userNo;
    private String content;


    @Builder(builderMethodName = "createBuilder", builderClassName = "createBuilder")
    public CommentRequestDto(Long postNo, Long parentNo, Long userNo, String content) {
        this.postNo = postNo;
        this.parentNo = parentNo;
        this.userNo = userNo;
        this.content = content;
    }

    @Builder(builderMethodName = "updateBuilder", builderClassName = "updateBuilder")
    public CommentRequestDto(Long commentNo, Long userNo, String content) {
        this.commentNo = commentNo;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "deleteBuilder", builderClassName = "deleteBuilder")
    public CommentRequestDto(Long commentNo, Long userNo) {
        this.commentNo = commentNo;
        this.userNo = userNo;
    }
}
