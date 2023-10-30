package com.inssa.server.api.bookmark.dto;

import com.inssa.server.share.bookmark.BookmarkType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkResponseDto {
	private Long no;
	private Long targetNo;
	private BookmarkType type;
	private LocalDateTime bookmarkedAt;
	private String summary;

	@Builder
	public BookmarkResponseDto(Long no, Long targetNo, BookmarkType type,
		LocalDateTime bookmarkedAt, String summary) {
		this.no = no;
		this.targetNo = targetNo;
		this.type = type;
		this.bookmarkedAt = bookmarkedAt;
		this.summary = summary;
	}
}
