package com.inssa.server.api.board.articlelike.model;

import com.inssa.server.api.board.article.model.Article;
import com.inssa.server.api.user.model.User;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ArticleLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no")
    private Long no;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", insertable = false, updatable = false)
    private User user;
    private Long userNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleNo", insertable = false, updatable = false)
    private Article article;
    private Long articleNo;
}
