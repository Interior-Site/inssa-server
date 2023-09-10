package com.inssa.server.api.board.postlike;

import com.inssa.server.api.board.postlike.service.PostLikeService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "postLike", description = "게시글 공감 API")
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class PostLikeController {
    private final PostLikeService postLikeService;

    @Operation(summary = "게시글 공감 추가", tags = "postLike")
    @PreAuthorizeLogInUser
    @PostMapping("/post/{postNo}/like")
    public InssaApiResponse<Map<String, Object>> createPostLike(
            @Parameter(description = "게시글 No") @PathVariable Long postNo,
            @AuthenticationPrincipal AuthUser user
    ) {
        postLikeService.createLike(postNo, user.getUserNo());
        return InssaApiResponse.ok(Map.of("postNo", postNo, "liked", true));
    }

    @Operation(summary = "게시글 공감 취소", tags = "postLike")
    @PreAuthorizeLogInUser
    @DeleteMapping("/post/{postNo}/like")
    public InssaApiResponse<Map<String, Object>> deletePostLike(
        @Parameter(description = "게시글 No") @PathVariable Long postNo,
        @AuthenticationPrincipal AuthUser user
    ) {
        postLikeService.deleteLike(postNo, user.getUserNo());
        return InssaApiResponse.ok(Map.of("postNo", postNo, "liked", false));
    }
}
