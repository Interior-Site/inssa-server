package com.inssa.server.api.bookmark.data;

import com.inssa.server.api.bookmark.model.Bookmark;
import com.inssa.server.api.bookmark.model.QBookmark;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BookmarkCustomRepositoryImpl implements BookmarkCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Bookmark> findUserBookmarks(Long userNo, Pageable pageable) {
		return jpaQueryFactory
			.select(QBookmark.bookmark)
			.from(QBookmark.bookmark)
			.where(QBookmark.bookmark.userNo.eq(userNo))
			.orderBy(QBookmark.bookmark.createdDate.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getOffset())
			.fetch();
	}
}
