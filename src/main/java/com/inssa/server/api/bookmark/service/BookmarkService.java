package com.inssa.server.api.bookmark.service;

import com.inssa.server.api.bookmark.data.BookmarkRepository;
import com.inssa.server.api.bookmark.dto.BookmarkRequestDto;
import com.inssa.server.api.bookmark.model.Bookmark;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service("BookmarkService")
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

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
}
