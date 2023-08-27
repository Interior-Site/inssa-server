package com.inssa.server.api.board.comment.comment.model;

import com.inssa.server.api.board.comment.like.model.CommentLike;
import com.inssa.server.api.board.post.model.Post;
import com.inssa.server.api.user.model.User;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long no;

    @Column(length = 1000, nullable = false)
    private String content;

    @ColumnDefault("'VISIBLE'")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardStatus status = BoardStatus.VISIBLE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no", nullable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_no", updatable = false)
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<Comment> children = new ArrayList<>();

    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private final List<CommentLike> likes = new ArrayList<>();

    @Builder
    protected Comment(String content, User user, Post post, Comment parent) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.parent = parent;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public Long getUserNo() {
        return Objects.isNull(this.user)? null : this.user.getNo();
    }

    public int getLikeCount() {
        return likes.size();
    }

    public void delete() {
        this.status = BoardStatus.DELETED;
    }
}
