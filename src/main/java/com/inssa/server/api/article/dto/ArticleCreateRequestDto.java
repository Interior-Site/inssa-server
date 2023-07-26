package com.inssa.server.api.article.dto;

import com.inssa.server.api.article.model.ArticleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "게시글 등록 API request")
public class ArticleCreateRequestDto {
	@Schema(description = "게시글 타입 ex. NORMAL, VOTE")
	private ArticleType articleType;
	@Schema(description = "게시글 제목")
	private String title;
	@Schema(description = "게시글 내용")
	private String content;
}
