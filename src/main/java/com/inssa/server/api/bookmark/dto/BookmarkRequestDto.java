package com.inssa.server.api.bookmark.dto;

import com.inssa.server.api.bookmark.model.Bookmark;
import com.inssa.server.share.bookmark.BookmarkType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class BookmarkRequestDto {
    private Long userNo;
    private BookmarkType type;
    private Long targetNo;
    private Long bookmarkNo;

    @Builder(builderMethodName = "createBuilder", builderClassName = "createBuilder")
    public BookmarkRequestDto(Long userNo, BookmarkType type, Long targetNo) {
        this.userNo = userNo;
        this.type = type;
        this.targetNo = targetNo;
    }

    @Builder(builderMethodName = "deleteBuilder", builderClassName = "deleteBuilder")
    public BookmarkRequestDto(Long userNo, Long bookmarkNo) {
        this.userNo = userNo;
        this.bookmarkNo = bookmarkNo;
    }

    public Bookmark toEntity() {
        return Bookmark.builder()
                .userNo(userNo)
                .type(type)
                .targetNo(targetNo)
                .build();
    }
}
