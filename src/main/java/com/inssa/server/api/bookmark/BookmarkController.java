package com.inssa.server.api.bookmark;

import com.inssa.server.api.bookmark.dto.BookmarkCreateRequestDto;
import com.inssa.server.api.bookmark.dto.BookmarkRequestDto;
import com.inssa.server.api.bookmark.service.BookmarkService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        return InssaApiResponse.ok(ResponseCode.CREATED, Map.of("bookmarkNo", bookmarkNo));
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
        return InssaApiResponse.ok(ResponseCode.DELETED, Map.of("bookmarkNo", deleteNo));
    }

}
