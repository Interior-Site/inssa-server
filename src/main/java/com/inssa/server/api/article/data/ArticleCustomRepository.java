package com.inssa.server.api.article.data;

import com.inssa.server.api.article.dto.ArticleListResponseDto;
import com.inssa.server.api.article.model.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleCustomRepository {
    Page<ArticleListResponseDto> findArticles(ArticleType type, Pageable pageable);
}
