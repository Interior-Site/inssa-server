package com.inssa.server.api.review.build.data;

import com.inssa.server.api.review.build.model.BuildReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildReviewRepository extends JpaRepository<BuildReview, Long> {
}
