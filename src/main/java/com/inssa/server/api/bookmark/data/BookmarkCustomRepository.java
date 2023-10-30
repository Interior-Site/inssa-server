package com.inssa.server.api.bookmark.data;

import com.inssa.server.api.board.post.dto.PostListResponseDto;
import com.inssa.server.api.board.post.dto.PostResponseDto;
import com.inssa.server.api.board.post.model.PostType;
import com.inssa.server.api.bookmark.model.Bookmark;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkCustomRepository {

    List<Bookmark> findUserBookmarks(Long userNo, Pageable pageable);
}
