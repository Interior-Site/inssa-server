package com.inssa.server.api.article.service;

import com.inssa.server.api.article.data.ArticleRepository;
import com.inssa.server.api.article.dto.ArticleListResponseDto;
import com.inssa.server.api.article.dto.ArticleRequestDto;
import com.inssa.server.api.article.model.Article;
import com.inssa.server.api.article.model.ArticleType;
import com.inssa.server.common.board.BoardStatus;
import com.inssa.server.common.exception.InssaException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("ArticleService")
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;

	public Long createArticle(ArticleRequestDto request) {
		Article article = articleRepository.save(request.toEntity());
		return article.getNo();
	}

	public Page<ArticleListResponseDto> findArticles(ArticleType type, Pageable pageable) {
		return articleRepository.findArticles(type, pageable);
	}

	public Long updateArticle(ArticleRequestDto request) {
		Article article = findById(request.getArticleNo());

		if(!Objects.equals(article.getUserNo(), request.getUserNo())) {
			throw new InssaException("작성자만 수정 가능합니다.");
		}

		if(article.getStatus() == BoardStatus.DELETED) {
			throw new InssaException("삭제된 글은 수정 불가능합니다.");
		}

		articleRepository.save(article.updateArticle(request.getTitle(), request.getContent()));

		return article.getNo();
	}

	public void deleteArticle(ArticleRequestDto request) {
		Article article = findById(request.getArticleNo());
		articleRepository.save(article.deleteArticle());
	}

	private Article findById(Long articleNo) {
		return articleRepository.findById(articleNo)
			.orElseThrow(() -> new InssaException("해당하는 게시글이 없습니다."));
	}
}
