package com.inssa.server.api.review.order.model;

import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.review.build_type.model.BuildType;
import com.inssa.server.api.review.order.model.OrderReviewBuildType;
import com.inssa.server.api.review.category.model.Category;
import com.inssa.server.api.review.like.model.OrderReviewLike;
import com.inssa.server.api.user.model.User;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.*;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity(name = "order_review")
public class OrderReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no")
    private Long no;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int amount;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob // Long Text (~4GB)
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'VISIBLE'")
    @Column(length = 10, nullable = false)
    private BoardStatus status;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", insertable = false, updatable = false, nullable = false)
    private User user;
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyNo", insertable = false, updatable = false, nullable = false)
    private Company company;
    private Long companyNo;

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderReviewBuildType> orderReviewBuildTypes = new ArrayList<>();

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderReviewCategory> orderReviewCategories = new ArrayList<>();

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderReviewLike> likes = new ArrayList<>();

    @Builder
    public OrderReview(int amount, String title, String content, Long userNo, Long companyNo) {
        this.amount = amount;
        this.title = title;
        this.content = content;
        this.userNo = userNo;
        this.companyNo = companyNo;
        this.status = BoardStatus.VISIBLE;
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

    public void updaddReviewBuildType(List<OrderReviewBuildType> buildTypes) {
        if (Objects.nonNull(this.orderReviewCategories)) {
            this.getOrderReviewBuildTypes().clear();
        }
        buildTypes.forEach(this::addReviewBuildType);
    }

    public void updateCompany(Company company) {
        if (!Objects.equals(company.getNo(), this.companyNo)){
            this.company = company;
        }
    }

    public OrderReview updateOrderReview(
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
        if (!Objects.equals(this.title, title) && Objects.nonNull(title) && !title.isBlank()) {
            this.title = title;
        }
        if (!Objects.equals(this.content, content) && Objects.nonNull(content) && !content.isBlank()) {
            this.content = content;
        }
        if (!Objects.equals(company.getNo(), this.companyNo)){
            this.company = company;
        }
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
        return this;
    }

    public void increaseViewCount() {
        this.viewCount ++;
    }

    public List<BuildType> getBuildTypes() {
        if (!this.orderReviewBuildTypes.isEmpty()) {
            return orderReviewBuildTypes.stream()
                    .map(OrderReviewBuildType::getBuildType)
                    .toList();
        }
        throw new InssaException(ErrorCode.INVALID, "건물 유형 정보가 올바르지 않습니다.");
    }

    public List<Category> getCategories() {
        if (!this.orderReviewCategories.isEmpty()){
            return orderReviewCategories.stream()
                    .map(OrderReviewCategory::getCategory)
                    .toList();
        }
        throw new InssaException(ErrorCode.INVALID, "시공 유형 정보가 올바르지 않습니다.");
    }

    public void delete() {
        this.status = BoardStatus.DELETED;
    }
}
