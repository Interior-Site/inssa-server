package com.inssa.server.api.review.order.model;

import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.review.build_type.model.BuildType;
import com.inssa.server.api.review.category.model.Category;
import com.inssa.server.api.review.comment.model.OrderReviewComment;
import com.inssa.server.api.review.like.model.OrderReviewLike;
import com.inssa.server.api.user.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.*;

@Getter
@DynamicInsert
@NoArgsConstructor
@Entity
public class OrderReview extends Review {

    @ColumnDefault("0")
    @Column(nullable = false)
    private int amount;

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderReviewBuildType> orderReviewBuildTypes = new ArrayList<>();

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderReviewCategory> orderReviewCategories = new ArrayList<>();

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderReviewLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderReviewComment> comments = new ArrayList<>();


    @Builder
    public OrderReview(String title, String content, User user, Company company, int amount) {
        super(title, content, user, company);
        this.amount = amount;
    }

    public int getCommentCount() {
        return comments.size();
    }

    public int getLikeCount() {
        return likes.size();
    }

    public void addReviewCategory(OrderReviewCategory category) {
        this.getOrderReviewCategories().add(category);
        category.setOrderReview(this);
    }

    public void addReviewBuildType(OrderReviewBuildType buildType) {
        this.getOrderReviewBuildTypes().add(buildType);
        buildType.setOrderReview(this);
    }

    public void updateReviewCategory(List<OrderReviewCategory> categories) {
        if (Objects.nonNull(this.orderReviewCategories)) {
            this.getOrderReviewCategories().clear();
        }
        categories.forEach(this::addReviewCategory);
    }

    public void updateReviewBuildType(List<OrderReviewBuildType> buildTypes) {
        if (Objects.nonNull(this.orderReviewCategories)) {
            this.getOrderReviewBuildTypes().clear();
        }
        buildTypes.forEach(this::addReviewBuildType);
    }

    public void update(
            int amount,
            String title,
            String content,
            Company company,
            List<OrderReviewBuildType> buildTypes,
            List<OrderReviewCategory> categories
    ) {
        if (!Objects.equals(this.amount, amount)) {
            this.amount = amount;
        }
        this.update(title, content, company);
        if (!this.orderReviewCategories.isEmpty()) {
            this.orderReviewCategories.clear();
        }
        if (!buildTypes.isEmpty()) {
            Set<OrderReviewBuildType> oldBuildTypes = new HashSet<>(this.orderReviewBuildTypes);
            if (buildTypes.stream().anyMatch(b -> !oldBuildTypes.contains(b))){
                this.orderReviewBuildTypes.clear();
                this.orderReviewBuildTypes.addAll(buildTypes);
            }
        }
        if (!categories.isEmpty() || !this.orderReviewCategories.isEmpty()) {
            Set<OrderReviewCategory> oldCategories = new HashSet<>(this.orderReviewCategories);
            if (categories.stream().anyMatch(b -> !oldCategories.contains(b))){
                this.orderReviewCategories.clear();
                this.orderReviewCategories.addAll(categories);
            }
        }
    }

    public List<BuildType> getBuildTypes() {
        if (!this.orderReviewBuildTypes.isEmpty()) {
            return orderReviewBuildTypes.stream()
                    .map(OrderReviewBuildType::getBuildType)
                    .toList();
        }
        return new ArrayList<>();
    }

    public List<Category> getCategories() {
        if (!this.orderReviewCategories.isEmpty()) {
            return orderReviewCategories.stream()
                    .map(OrderReviewCategory::getCategory)
                    .toList();
        }
        return new ArrayList<>();
    }
}

