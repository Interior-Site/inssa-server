package com.inssa.server.api.board.post.data;

import com.inssa.server.api.board.post.dto.PostListResponseDto;
import com.inssa.server.api.board.post.dto.PostResponseDto;
import com.inssa.server.api.board.post.dto.QPostListResponseDto;
import com.inssa.server.api.board.post.dto.QPostResponseDto;
import com.inssa.server.api.board.post.model.PostType;
import com.inssa.server.api.board.post.model.QPost;
import com.inssa.server.api.board.postlike.model.QPostLike;
import com.inssa.server.api.user.model.QUser;
import com.inssa.server.share.board.BoardStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostListResponseDto> findPosts(PostType type, Pageable pageable) {
        List<PostListResponseDto> result = jpaQueryFactory
                .select(new QPostListResponseDto(
                    QPost.post.no,
                    QPost.post.type,
                    QPost.post.title,
                    QPost.post.viewCount,
                    QPost.post.userNo,
                    QUser.user.nickname,
                    QPostLike.postLike.count()
                ))
                .from(QPost.post)
                .leftJoin(QUser.user).on(QPost.post.userNo.eq(QUser.user.no))
                .fetchJoin()
                .leftJoin(QPostLike.postLike).on(QPost.post.no.eq(QPostLike.postLike.postNo))
                .fetchJoin()
                .where(QPost.post.type.eq(type)
                        .and(QPost.post.status.eq(BoardStatus.VISIBLE)))
                .groupBy(QPost.post.no)
                .orderBy(QPost.post.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public PostResponseDto findPost(Long postNo) {
        return jpaQueryFactory
            .select(new QPostResponseDto(
                QPost.post.no,
                QPost.post.type,
                QPost.post.title,
                QPost.post.content,
                QPost.post.viewCount,
                QPost.post.userNo,
                QUser.user.nickname,
                QPostLike.postLike.count()
            ))
            .from(QPost.post)
            .leftJoin(QUser.user).on(QPost.post.userNo.eq(QUser.user.no))
            .fetchJoin()
            .leftJoin(QPostLike.postLike).on(QPost.post.no.eq(QPostLike.postLike.postNo))
            .fetchJoin()
            .where(QPost.post.no.eq(postNo)
                .and(QPost.post.status.eq(BoardStatus.VISIBLE)))
            .groupBy(QPost.post.no)
            .fetchOne();
    }
}
