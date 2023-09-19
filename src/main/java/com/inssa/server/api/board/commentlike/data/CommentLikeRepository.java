package com.inssa.server.api.board.commentlike.data;

import com.inssa.server.api.board.commentlike.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserNoAndCommentNo(Long userNo, Long commentNo);

    boolean existsByUserNoAndCommentNo(Long userNo, Long commentNo);

    long countByCommentNo(Long commentNo);
}
