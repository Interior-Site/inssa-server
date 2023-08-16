package com.inssa.server.api.review.like.model;

import com.inssa.server.api.review.build.model.BuildReview;
import com.inssa.server.api.user.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "BuildReviewLike",
        uniqueConstraints = {
            @UniqueConstraint(name = "uc_buildreviewlike_user_no", columnNames = {"user_no", "build_review_no"}
        )
})
public class BuildReviewLike extends ReviewLike {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_review_no", nullable = false, updatable = false)
    private BuildReview buildReview;

    @Builder
    public BuildReviewLike(User user, BuildReview buildReview) {
        super(user);
        this.buildReview = buildReview;
    }
}