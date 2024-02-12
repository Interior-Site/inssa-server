package com.inssa.server.api.bookmark;

import com.inssa.server.api.bookmark.dto.BookmarkCreateRequestDto;
import com.inssa.server.api.bookmark.dto.BookmarkRequestDto;
import com.inssa.server.api.bookmark.dto.BookmarkResponseDto;
import com.inssa.server.api.bookmark.service.BookmarkService;
import com.inssa.server.api.user.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "bookmark", description = "북마크 API")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 생성", tags = "bookmark")
    @PreAuthorizeLogInUser
    @PostMapping("/api/v1/bookmark")
    public InssaApiResponse<Map<String, Object>> createBookmark(@RequestBody BookmarkCreateRequestDto request, @AuthenticationPrincipal AuthUser user) {
        BookmarkRequestDto serviceRequest = BookmarkRequestDto.createBuilder()
                .userNo(user.getUserNo())
                .type(request.getType())
                .targetNo(request.getTargetNo())
                .build();

        Long bookmarkNo = bookmarkService.createBookmark(serviceRequest);

        return InssaApiResponse.success(ResponseCode.CREATED, Map.of("bookmarkNo", bookmarkNo));
    }

    @Operation(summary = "북마크 삭제", tags = "bookmark")
    @PreAuthorizeLogInUser
    @DeleteMapping("/api/v1/bookmark/{bookmarkNo}")
    public InssaApiResponse<Map<String, Object>> deleteBookmark(@PathVariable Long bookmarkNo, @AuthenticationPrincipal AuthUser user) {
        BookmarkRequestDto serviceRequest = BookmarkRequestDto.deleteBuilder()
                .userNo(user.getUserNo())
                .bookmarkNo(bookmarkNo)
                .build();

        Long deleteNo = bookmarkService.deleteBookmark(serviceRequest);
        return InssaApiResponse.success(ResponseCode.DELETED, Map.of("bookmarkNo", deleteNo));
    }

    public InssaApiResponse<Page<BookmarkResponseDto>> findBookmarks(
        @SortDefault(sort = "created_date", direction = Sort.Direction.DESC) Pageable pageable,
        @AuthenticationPrincipal AuthUser user) {
        return InssaApiResponse.success(bookmarkService.findBookmarks(user.getUserNo(), pageable));
    }

}
