package com.inssa.server.api.review.order.model;

import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.user.model.User;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    private Long no;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'VISIBLE'")
    @Column(length = 10, nullable = false)
    private BoardStatus status = BoardStatus.VISIBLE;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int viewCount = 0;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_no", nullable = false, updatable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_no", nullable = false)
    private Company company;

    protected Review(String title, String content, User user, Company company) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.company = company;
    }

    public void update(String title, String content, Company company) {
        if (Objects.nonNull(title) && !title.isBlank() && !Objects.equals(this.title, title)) {
            this.title = title;
        }
        if (Objects.nonNull(content) && !content.isBlank() && !Objects.equals(this.content, content)) {
            this.content = content;
        }
        if (Objects.nonNull(company) && !Objects.equals(company, this.company)) {
            this.company = company;
        }
    }

    public Long getUserNo() {
        return Objects.nonNull(user) ? user.getNo() : null;
    }

    public void increaseViewCount() {
        this.viewCount ++;
    }

    public void delete() {
        this.status = BoardStatus.DELETED;
    }

}
