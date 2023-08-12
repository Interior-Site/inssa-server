package com.inssa.server.api.board.article.data;

import com.inssa.server.api.board.article.dto.ArticleListResponseDto;
import com.inssa.server.api.board.article.dto.ArticleResponseDto;
import com.inssa.server.api.board.article.dto.QArticleListResponseDto;
import com.inssa.server.api.board.article.dto.QArticleResponseDto;
import com.inssa.server.api.board.article.model.ArticleType;
import com.inssa.server.api.board.article.model.QArticle;
import com.inssa.server.api.board.articlelike.model.QArticleLike;
import com.inssa.server.api.user.model.QUser;
import com.inssa.server.share.board.BoardStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ArticleListResponseDto> findArticles(ArticleType type, Pageable pageable) {
        List<ArticleListResponseDto> result = jpaQueryFactory
                .select(new QArticleListResponseDto(
                        QArticle.article.no,
                        QArticle.article.type,
                        QArticle.article.title,
                        QArticle.article.viewCount,
                        QArticle.article.userNo,
                        QUser.user.nickname,
                        QArticleLike.articleLike.count()
                ))
                .from(QArticle.article)
                .leftJoin(QUser.user).on(QArticle.article.userNo.eq(QUser.user.no))
                .fetchJoin()
                .leftJoin(QArticleLike.articleLike).on(QArticle.article.no.eq(QArticleLike.articleLike.articleNo))
                .fetchJoin()
                .where(QArticle.article.type.eq(type)
                        .and(QArticle.article.status.eq(BoardStatus.VISIBLE)))
                .groupBy(QArticle.article.no)
                .orderBy(QArticle.article.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public ArticleResponseDto findArticle(Long articleNo) {
        return jpaQueryFactory
            .select(new QArticleResponseDto(
                QArticle.article.no,
                QArticle.article.type,
                QArticle.article.title,
                QArticle.article.content,
                QArticle.article.viewCount,
                QArticle.article.userNo,
                QUser.user.nickname
            ))
            .from(QArticle.article)
            .leftJoin(QUser.user).on(QArticle.article.userNo.eq(QUser.user.no))
            .fetchJoin()
            .where(QArticle.article.no.eq(articleNo)
                .and(QArticle.article.status.eq(BoardStatus.VISIBLE)))
            .fetchOne();
    }
}
