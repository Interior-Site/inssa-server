package com.inssa.server.api.board.post.model;

import com.inssa.server.api.user.model.User;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private Long no;

    @Enumerated(EnumType.STRING)
    private PostType type;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;
    private int viewCount = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNo", insertable = false, updatable = false)
    private User user;
    private Long userNo;

    public Post updatePost(String title, String content) {
        if(title != null && !title.trim().equals("")) {
            this.title = title;
        }

        if(content != null && !content.trim().equals("")) {
            this.content = content;
        }

        return this;
    }

    public Post deletePost() {
        this.status = BoardStatus.DELETED;
        return this;
    }

    @Builder
    public Post(PostType type, String title, String content, Long userNo) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.status = BoardStatus.VISIBLE;
        this.userNo = userNo;
    }
}
