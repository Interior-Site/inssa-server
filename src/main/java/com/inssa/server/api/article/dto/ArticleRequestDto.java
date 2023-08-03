package com.inssa.server.api.article.dto;

import com.inssa.server.api.article.model.Article;
import com.inssa.server.api.article.model.ArticleType;
import com.inssa.server.share.board.BoardStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class ArticleRequestDto {
	private Long articleNo;
	private ArticleType articleType;
	private String title;
	private String content;
	private Long userNo;

	@Builder(builderMethodName = "createBuilder")
	public ArticleRequestDto(ArticleType articleType, String title, String content, Long userNo) {
		this.articleType = articleType;
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}

	@Builder(builderMethodName = "updateBuilder")
	public ArticleRequestDto(Long articleNo, String title, String content, Long userNo) {
		this.articleNo = articleNo;
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}

	@Builder(builderMethodName = "deleteBuilder")
	public ArticleRequestDto(Long articleNo, Long userNo) {
		this.articleNo = articleNo;
		this.userNo = userNo;
	}

	public Article toEntity() {
		return Article.builder()
			.type(articleType)
			.title(title)
			.content(content)
			.userNo(userNo)
			.status(BoardStatus.VISIBLE)
			.build();
	}
}
