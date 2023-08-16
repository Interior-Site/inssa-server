package com.inssa.server.api.review.build.model;

import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.user.model.User;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity(name = "build_review")
public class BuildReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_no")
    private Long no;

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

    @Builder
    public BuildReview(String title, String content, Long userNo, Long companyNo) {
        this.title = title;
        this.content = content;
        this.userNo = userNo;
        this.companyNo = companyNo;
        this.status = BoardStatus.VISIBLE;
    }
}
