package com.inssa.server.share.bookmark;

import com.inssa.server.api.board.post.service.PostBookmarkSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkSummaryMapping {
	private final PostBookmarkSummaryService postBookmarkSummaryService;

	public BookmarkSummary mapping(BookmarkType type) {
		if (type.equals(BookmarkType.POST)) {
			return postBookmarkSummaryService;
		} else {
			return null;
		}
	}
}
