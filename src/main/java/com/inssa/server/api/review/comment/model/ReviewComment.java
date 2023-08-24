package com.inssa.server.api.review.comment.model;

import com.inssa.server.api.review.order.model.Review;
import com.inssa.server.api.user.model.User;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class ReviewComment<R extends Review, C extends ReviewComment<R, C>> extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long no;

    @Column(length = 1000, nullable = false)
    private String content;

    @ColumnDefault("1")
    @Column(nullable = false)
    private final boolean deleted = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_no", nullable = false, updatable = false)
    private R review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_no", updatable = false)
    private C parent;

    protected ReviewComment(String content, User user, R review, C parent) {
        this.content = content;
        this.user = user;
        this.review = review;
        this.parent = parent;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public Long getUserNo() {
        return Objects.isNull(this.user)? null : this.user.getNo();
    }
}
