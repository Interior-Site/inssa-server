package com.inssa.server.api.board.post.data;

import com.inssa.server.api.board.post.dto.PostListResponseDto;
import com.inssa.server.api.board.post.dto.PostResponseDto;
import com.inssa.server.api.board.post.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {
    Page<PostListResponseDto> findPosts(PostType type, Pageable pageable);

    PostResponseDto findPost(Long postNo);
}
