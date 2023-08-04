package com.inssa.server.api.board.articlelike.service;

import com.inssa.server.api.board.articlelike.data.ArticleLikeRepository;
import com.inssa.server.api.board.articlelike.model.ArticleLike;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("ArticleLikeService")
@RequiredArgsConstructor
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;

    public void createLike(Long articleNo, Long userNo) {
        Optional<ArticleLike> findArticleLike = articleLikeRepository.findByUserNoAndArticleNo(userNo, articleNo);

        if(findArticleLike.isPresent()) {
            throw new InssaException("이미 공감한 글입니다.");
        }

        articleLikeRepository.save(new ArticleLike(userNo, articleNo));
    }
}
