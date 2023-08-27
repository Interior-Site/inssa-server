package com.inssa.server.api.board.comment.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final Long postNo;
    private Long commentNo;
    private Long parentNo;
    private final Long userNo;
    private String content;


    @Builder(builderMethodName = "createBuilder", builderClassName = "createBuilder")
    public CommentRequestDto(Long postNo, String content, Long parentNo, Long userNo) {
        this.postNo = postNo;
        this.parentNo = parentNo;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "updateBuilder", builderClassName = "updateBuilder")
    public CommentRequestDto(Long postNo, Long commentNo, String content, Long userNo) {
        this.postNo = postNo;
        this.commentNo = commentNo;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "deleteBuilder", builderClassName = "deleteBuilder")
    public CommentRequestDto(Long postNo, Long commentNo, Long userNo) {
        this.postNo = postNo;
        this.commentNo = commentNo;
        this.userNo = userNo;
    }

}
