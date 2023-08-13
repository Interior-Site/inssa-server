package com.inssa.server.api.board.article.data;

import com.inssa.server.api.board.article.dto.ArticleListResponseDto;
import com.inssa.server.api.board.article.dto.ArticleResponseDto;
import com.inssa.server.api.board.article.model.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleCustomRepository {
    Page<ArticleListResponseDto> findArticles(ArticleType type, Pageable pageable);

    ArticleResponseDto findArticle(Long articleNo);
}
