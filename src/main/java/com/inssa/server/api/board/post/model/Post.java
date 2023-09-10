package com.inssa.server.api.board.post.model;

import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.bookmark.BookmarkType;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Enumerated(EnumType.STRING)
    private BoardStatus status = BoardStatus.VISIBLE;

    @Enumerated(EnumType.STRING)
    private BookmarkType type;
}
