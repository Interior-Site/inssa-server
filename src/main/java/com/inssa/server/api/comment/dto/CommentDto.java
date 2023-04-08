package com.inssa.server.api.comment.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Data // 자동으로 getter, setter, toString 등
public class CommentDto {

    private int commentNo; // 댓글번호
    private int parentCommentNo; // 부모 댓글 번호
    private String content; // 댓글내용
    private String commentStatus; // 댓글상태
    private LocalDateTime commentRegDate; // 댓글 등록일
    private LocalDateTime commentChgDate; // 댓글 수정일
    private int boardNo; // 게시글번호
    private String userId; // 작성자ID



}
