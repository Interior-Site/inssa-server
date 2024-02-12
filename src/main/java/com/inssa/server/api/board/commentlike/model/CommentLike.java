package com.inssa.server.api.board.commentlike.model;

import com.inssa.server.api.board.comment.model.Comment;
import com.inssa.server.api.user.user.model.User;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userNo", insertable = false, updatable = false)
    private User user;
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commentNo", insertable = false, updatable = false)
    private Comment comment;
    private Long commentNo;

    @Builder
    public CommentLike(Long userNo, Long commentNo) {
        this.userNo = userNo;
        this.commentNo = commentNo;
    }

    public void changeComment(Comment comment) {
        this.comment = comment;
    }
}
