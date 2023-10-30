package com.inssa.server.api.bookmark.service;

import com.inssa.server.api.bookmark.data.BookmarkRepository;
import com.inssa.server.api.bookmark.dto.BookmarkResponseDto;
import com.inssa.server.api.bookmark.dto.BookmarkRequestDto;
import com.inssa.server.api.bookmark.model.Bookmark;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.share.bookmark.BookmarkSummary;
import com.inssa.server.share.bookmark.BookmarkSummaryMapping;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("BookmarkService")
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkSummaryMapping bookmarkSummaryMapping;

    public Long createBookmark(BookmarkRequestDto request) {
        Bookmark bookmark = bookmarkRepository.save(request.toEntity());
        return bookmark.getNo();
    }

    public Long deleteBookmark(BookmarkRequestDto request) {
        Bookmark bookmark = bookmarkRepository.findById(request.getBookmarkNo())
                .orElseThrow(() -> new InssaException("해당하는 북마크가 없습니다. bookmarkNo: " + request.getBookmarkNo()));

        if(!Objects.equals(bookmark.getUserNo(), request.getUserNo())) {
            throw new InssaException("북마크한 회원이 아닙니다.");
        }

        bookmarkRepository.delete(bookmark);

        return request.getBookmarkNo();
    }

    public Page<BookmarkResponseDto> findBookmarks(Long userNo, Pageable pageable) {
        List<Bookmark> rawBookmarks = bookmarkRepository.findUserBookmarks(userNo, pageable);
        List<BookmarkResponseDto> responseList = new ArrayList<>();

        for(Bookmark bookmark : rawBookmarks) {
            BookmarkSummary bookmarkSummary = bookmarkSummaryMapping.mapping(bookmark.getType());
            String summary = bookmarkSummary.findBookmarkSummaries(bookmark.getTargetNo());

            BookmarkResponseDto response = BookmarkResponseDto.builder()
                .no(bookmark.getNo())
                .type(bookmark.getType())
                .targetNo(bookmark.getTargetNo())
                .bookmarkedAt(bookmark.getCreatedDate())
                .summary(summary)
                .build();

            responseList.add(response);
        }

        return new PageImpl<>(responseList, pageable, responseList.size());
    }
}
