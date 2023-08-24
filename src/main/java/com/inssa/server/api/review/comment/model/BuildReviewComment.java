package com.inssa.server.api.review.comment.model;

import com.inssa.server.api.review.build.model.BuildReview;
import com.inssa.server.api.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BuildReviewComment extends ReviewComment<BuildReview, BuildReviewComment> {

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<BuildReviewComment> children = new ArrayList<>();

    @OneToMany(mappedBy = "reviewComment", orphanRemoval = true)
    private final List<BuildReviewCommentLike> likes = new ArrayList<>();

    public int getLikeCount() {
        return likes.size();
    }

    @Builder
    public BuildReviewComment(String content, User user, BuildReview review, BuildReviewComment parent) {
        super(content, user, review, parent);
    }
}
