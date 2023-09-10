package com.inssa.server.api.board.post.dto;

import com.inssa.server.api.board.post.model.Post;
import com.inssa.server.api.board.post.model.PostType;
import com.inssa.server.api.board.post.model.PostType;
import com.inssa.server.share.board.BoardStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class PostRequestDto {
	private Long postNo;
	private PostType postType;
	private String title;
	private String content;
	private Long userNo;

	@Builder(builderMethodName = "createBuilder", builderClassName = "createBuilder")
	public PostRequestDto(PostType postType, String title, String content, Long userNo) {
		this.postType = postType;
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}

	@Builder(builderMethodName = "updateBuilder", builderClassName = "updateBuilder")
	public PostRequestDto(Long postNo, String title, String content, Long userNo) {
		this.postNo = postNo;
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}

	@Builder(builderMethodName = "deleteBuilder", builderClassName = "deleteBuilder")
	public PostRequestDto(Long postNo, Long userNo) {
		this.postNo = postNo;
		this.userNo = userNo;
	}

	public Post toEntity() {
		return Post.builder()
			.type(postType)
			.title(title)
			.content(content)
			.userNo(userNo)
			.build();
	}
}
