package com.inssa.server.api.article.dto;

import com.inssa.server.api.article.model.ArticleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "게시글 수정 API request")
public class ArticleUpdateRequestDto {
	@Schema(description = "게시글 번호")
	private Long articleNo;
	@Schema(description = "게시글 제목")
	private String title;
	@Schema(description = "게시글 내용")
	private String content;
}
