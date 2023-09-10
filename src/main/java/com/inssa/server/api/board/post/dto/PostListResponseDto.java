package com.inssa.server.api.board.post.dto;

import com.inssa.server.api.board.post.model.PostType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    /**
     * 게시글 번호
     */
    private Long postNo;

    /**
     * 게시글 타입
     */
    private PostType type;

    /**
     * 게시글 제목
     */
    private String title;

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

    // 추후 댓글 개수 추가해야 함

    @QueryProjection
    public PostListResponseDto(Long postNo, PostType type, String title, int viewCount, Long writerNo, String writerNickname, Long likeCount) {
        this.postNo = postNo;
        this.type = type;
        this.title = title;
        this.viewCount = viewCount;
        this.writerNo = writerNo;
        this.writerNickname = writerNickname;
        this.likeCount = likeCount;
    }
}
