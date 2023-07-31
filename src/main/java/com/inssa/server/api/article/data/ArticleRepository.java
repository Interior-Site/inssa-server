package com.inssa.server.api.article.data;

import com.inssa.server.api.article.dto.ArticleListResponseDto;
import com.inssa.server.api.article.model.Article;
import com.inssa.server.api.article.model.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleCustomRepository{
}