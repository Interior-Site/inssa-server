package com.inssa.server.api.bookmark.model;

import com.inssa.server.api.user.model.User;
import com.inssa.server.common.entity.BaseTimeEntity;
import com.inssa.server.share.bookmark.BookmarkType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Bookmark extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_no")
    private Long no;

    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", insertable = false, updatable = false, nullable = true,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user = null;

    @Enumerated(EnumType.STRING)
    private BookmarkType type;
    private Long targetNo;

    @Builder
    public Bookmark(Long userNo, BookmarkType type, Long targetNo) {
        this.userNo = userNo;
        this.type = type;
        this.targetNo = targetNo;
    }
}
