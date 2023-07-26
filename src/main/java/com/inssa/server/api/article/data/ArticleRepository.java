package com.inssa.server.api.article.data;

import com.inssa.server.api.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
