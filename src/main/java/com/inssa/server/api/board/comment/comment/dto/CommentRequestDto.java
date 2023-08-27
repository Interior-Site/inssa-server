package com.inssa.server.api.board.comment.comment.dto;

import com.inssa.server.share.bookmark.BookmarkType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final Long postNo;
    private Long commentNo;
    private Long parentNo;
    private final Long userNo;
    private BookmarkType type;
    private String content;


    @Builder(builderMethodName = "createBuilder", builderClassName = "createBuilder")
    public CommentRequestDto(Long postNo, Long parentNo, Long userNo, BookmarkType type, String content) {
        this.postNo = postNo;
        this.parentNo = parentNo;
        this.userNo = userNo;
        this.content = content;
        this.type = type;
    }

    @Builder(builderMethodName = "updateBuilder", builderClassName = "updateBuilder")
    public CommentRequestDto(Long postNo, Long commentNo, Long userNo, String content) {
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
