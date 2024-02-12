package com.inssa.server.api.board.comment.model;

import com.inssa.server.api.board.commentlike.model.CommentLike;
import com.inssa.server.api.board.post.model.Post;
import com.inssa.server.api.user.user.model.User;
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
    @JoinColumn(name = "userNo", nullable = false, insertable = false, updatable = false)
    private User user;
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postNo", nullable = false, insertable = false, updatable = false)
    private Post post;
    private Long postNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCommentNo", insertable = false, updatable = false)
    private Comment parent;
    private Long parentCommentNo;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<Comment> children = new ArrayList<>();

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<CommentLike> likes = new ArrayList<>();

    @Builder
    protected Comment(String content, Long userNo, Long postNo, Long parentCommentNo) {
        this.content = content;
        this.userNo = userNo;
        this.postNo = postNo;
        this.parentCommentNo = parentCommentNo;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public Long getUserNo() {
        return Objects.isNull(this.user)? null : this.user.getNo();
    }

    public long getLikeCount() {
        return likes.size();
    }

    public void addCommentLike(CommentLike commentLike) {
        likes.add(commentLike);

    }

    public void delete() {
        this.status = BoardStatus.DELETED;
    }
}
