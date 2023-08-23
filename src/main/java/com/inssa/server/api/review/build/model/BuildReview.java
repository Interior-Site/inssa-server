package com.inssa.server.api.review.build.model;

import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.review.order.model.Review;
import com.inssa.server.api.user.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity(name = "build_review")
public class BuildReview extends Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_no")
    private Long no;

    @Builder
    public BuildReview(String title, String content, User user, Company company) {
        super(title, content, user, company);
    }
}
