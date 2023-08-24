package com.inssa.server.api.review.comment.model;

import com.inssa.server.api.review.build.model.BuildReview;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class BuildReviewComment extends ReviewComment<BuildReview, BuildReviewComment> {

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BuildReviewComment> children = new ArrayList<>();

    @OneToMany(mappedBy = "reviewComment", orphanRemoval = true)
    private List<BuildReviewCommentLike> likes = new ArrayList<>();

    public int getLikeCount() {
        return likes.size();
    }
}
