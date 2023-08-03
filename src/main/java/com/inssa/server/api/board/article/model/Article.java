package com.inssa.server.api.board.article.model;

import com.inssa.server.api.user.model.User;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Article extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_no")
	private Long no;

	@Enumerated(EnumType.STRING)
	private ArticleType type;

	private String title;
	private String content;

	@Enumerated(EnumType.STRING)
	private BoardStatus status;
	private int viewCount = 0;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userNo", insertable = false, updatable = false)
	private User user;
	private Long userNo;

	public Article updateArticle(String title, String content) {
		if(title != null && !title.trim().equals("")) {
			this.title = title;
		}

		if(content != null && !content.trim().equals("")) {
			this.content = content;
		}

		return this;
	}

	public Article deleteArticle() {
		this.status = BoardStatus.DELETED;
		return this;
	}

	@Builder
	public Article(ArticleType type, String title, String content, BoardStatus status,
		Long userNo) {
		this.type = type;
		this.title = title;
		this.content = content;
		this.status = status;
		this.userNo = userNo;
	}
}
