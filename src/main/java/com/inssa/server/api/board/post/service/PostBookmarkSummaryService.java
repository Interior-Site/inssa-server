package com.inssa.server.api.board.post.service;

import com.inssa.server.share.bookmark.BookmarkSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostBookmarkSummaryService implements BookmarkSummary {
	private final PostService postService;

	@Override
	public String findBookmarkSummaries(Long id) {
		return postService.findPostSummaryById(id);
	}
}
