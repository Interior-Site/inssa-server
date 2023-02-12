package com.inssa.server.api.comment.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
@Data
public class CommentDto {

    private int commentNo; // 댓글번호
    private String commentContent; // 댓글내용
    private String commentStatus; // 댓글상태
    private Timestamp commentRegDate; // 댓글 등록일
    private String commentImg; // 첨부파일
    private int boardNo; // 게시글번호
    private int userNo; // 작성자번호

// 테스트

}
