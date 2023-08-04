package com.inssa.server.api.board.articlelike.service;

import com.inssa.server.api.board.articlelike.data.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("ArticleLikeService")
@RequiredArgsConstructor
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;

    public void createLike(Long articleNo, Long userNo) {

    }
}
