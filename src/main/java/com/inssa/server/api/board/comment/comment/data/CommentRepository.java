package com.inssa.server.api.board.comment.comment.data;

import com.inssa.server.api.board.comment.comment.model.Comment;
import com.inssa.server.share.board.BoardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByStatusAndNo(BoardStatus status, Long commentNo);

    Page<Comment> findByParentNullAndStatusAndPostNo(BoardStatus status, Long postNo, Pageable pageable);
}
