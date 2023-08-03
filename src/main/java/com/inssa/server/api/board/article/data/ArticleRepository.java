package com.inssa.server.api.board.article.data;

import com.inssa.server.api.board.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleCustomRepository{
}
