package com.inssa.server.api.board.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "게시글 수정 API request")
public class PostUpdateRequestDto {
	@Schema(description = "게시글 번호")
	private Long postNo;
	@Schema(description = "게시글 제목")
	private String title;
	@Schema(description = "게시글 내용")
	private String content;
}
