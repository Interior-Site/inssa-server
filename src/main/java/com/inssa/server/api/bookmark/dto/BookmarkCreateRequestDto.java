package com.inssa.server.api.bookmark.dto;

import com.inssa.server.share.bookmark.BookmarkType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "북마크 생성 API request")
public class BookmarkCreateRequestDto {
    @Schema(description = "북마크 대상 타입")
    private BookmarkType type;
    @Schema(description = "북마크 대상 넘버")
    private Long targetNo;
}
