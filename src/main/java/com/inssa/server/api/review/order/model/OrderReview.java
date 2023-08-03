package com.inssa.server.api.review.order.model;

import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.user.model.User;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
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
    private List<OrderReviewBuildType> orderReviewBuildTypes = new ArrayList<>();

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderReviewCategory> orderReviewCategories = new ArrayList<>();

    @Builder
    public OrderReview(int amount, String title, String content, BoardStatus status, Long userNo, Long companyNo) {
        this.amount = amount;
        this.title = title;
        this.content = content;
        this.status = status;
        this.userNo = userNo;
        this.companyNo = companyNo;
    }
}
