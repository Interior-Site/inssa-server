package com.inssa.server.api.board.comment.like.model;

import com.inssa.server.api.board.comment.comment.model.Comment;
import com.inssa.server.api.user.model.User;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommentLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comment_no", updatable = false)
    private Comment comment;

    @Builder
    protected CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
