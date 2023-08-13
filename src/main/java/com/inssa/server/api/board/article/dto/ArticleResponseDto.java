package com.inssa.server.api.board.article.dto;

import com.inssa.server.api.board.article.model.ArticleType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
    /**
     * 게시글 번호
     */
    private Long articleNo;

    /**
     * 게시글 타입
     */
    private ArticleType type;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 내용
     */
    private String content;

    /**
     * 조회 수
     */
    private int viewCount;

    /**
     * 작성자 번호
     */
    private Long writerNo;

    /**
     * 작성자 닉네임
     */
    private String writerNickname;

    /**
     * 좋아요 개수
     */
    private Long likeCount;

    @QueryProjection
    public ArticleResponseDto(Long articleNo, ArticleType type, String title, String content, int viewCount, Long writerNo, String writerNickname, Long likeCount) {
        this.articleNo = articleNo;
        this.type = type;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.writerNo = writerNo;
        this.writerNickname = writerNickname;
        this.likeCount = likeCount;
    }
}
