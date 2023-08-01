package com.inssa.server.api.review.order.model;

import com.inssa.server.api.user.model.User;
import com.inssa.server.common.board.BoardStatus;
import com.inssa.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
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

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'VISIBLE'")
    @Column(length = 10, nullable = false)
    private BoardStatus status;

    @ColumnDefault("0")
    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", insertable = false, updatable = false, nullable = false)
    private User user;

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderBuildTag> buildTags = new ArrayList<>();

    @OneToMany(mappedBy = "orderReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderPlaceTag> placeTags = new ArrayList<>();
}