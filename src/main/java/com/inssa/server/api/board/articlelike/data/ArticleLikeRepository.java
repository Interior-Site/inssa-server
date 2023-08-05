package com.inssa.server.api.board.articlelike.data;

import com.inssa.server.api.board.articlelike.model.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    Optional<ArticleLike> findByUserNoAndArticleNo(Long userNo, Long articleNo);
}
