package com.inssa.server.api.board.articlelike.data;

import com.inssa.server.api.board.articlelike.model.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
}
