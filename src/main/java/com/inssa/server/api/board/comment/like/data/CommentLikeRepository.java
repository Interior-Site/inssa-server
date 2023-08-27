package com.inssa.server.api.board.comment.like.data;

import com.inssa.server.api.board.comment.like.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserNoAndCommentNo(Long userNo, Long commentNo);
}
