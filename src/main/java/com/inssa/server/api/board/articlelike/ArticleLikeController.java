package com.inssa.server.api.board.articlelike;

import com.inssa.server.api.board.articlelike.service.ArticleLikeService;
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
@Tag(name = "articleLike", description = "게시글 공감 API")
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    @Operation(summary = "게시글 공감 추가", tags = "articleLike")
    @PreAuthorizeLogInUser
    @PostMapping("/article/{articleNo}/like")
    public InssaApiResponse<Map<String, Object>> createArticleLike(
            @Parameter(description = "게시글 No") @PathVariable Long articleNo,
            @AuthenticationPrincipal AuthUser user
    ) {
        articleLikeService.createLike(articleNo, user.getUserNo());
        return InssaApiResponse.success(Map.of("articleNo", articleNo, "liked", true));
    }

    @Operation(summary = "게시글 공감 취소", tags = "articleLike")
    @PreAuthorizeLogInUser
    @DeleteMapping("/article/{articleNo}/like")
    public InssaApiResponse<Map<String, Object>> deleteArticleLike(
        @Parameter(description = "게시글 No") @PathVariable Long articleNo,
        @AuthenticationPrincipal AuthUser user
    ) {
        articleLikeService.deleteLike(articleNo, user.getUserNo());
        return InssaApiResponse.success(Map.of("articleNo", articleNo, "liked", false));
    }
}
