package com.inssa.server.api.board.post.data;

import com.inssa.server.api.board.post.model.Post;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.share.bookmark.BookmarkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
    Optional<Post> findByStatusAndNoAndType(BoardStatus boardStatus, Long postNo, BookmarkType type);
    Optional<Post> findByStatusAndNo(BoardStatus boardStatus, Long postNo);
}
