package com.inssa.server.api.board.commentlike;

import com.inssa.server.api.board.commentlike.dto.CommentLikeResponseDto;
import com.inssa.server.api.board.commentlike.service.CommentLikeService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "commentLike", description = "댓글 공감 API")
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @Operation(summary = "댓글 공감 추가", tags = "commentLike")
    @PreAuthorizeLogInUser
    @PostMapping("/comment/{commentNo}/like")
    public InssaApiResponse<CommentLikeResponseDto> createPostLike(
            @Parameter(description = "댓글 No") @PathVariable Long commentNo,
            @AuthenticationPrincipal AuthUser user
    ) {
        CommentLikeResponseDto likeResponse = commentLikeService.createLike(commentNo, user.getUserNo());
        return InssaApiResponse.success(ResponseCode.CREATED, likeResponse);
    }

    @Operation(summary = "댓글 공감 취소", tags = "commentLike")
    @PreAuthorizeLogInUser
    @DeleteMapping("/comment/{commentNo}/like")
    public InssaApiResponse<CommentLikeResponseDto> deletePostLike(
        @Parameter(description = "댓글 No") @PathVariable Long commentNo,
        @AuthenticationPrincipal AuthUser user
    ) {
        CommentLikeResponseDto likeResponse = commentLikeService.deleteLike(commentNo, user.getUserNo());
        return InssaApiResponse.success(ResponseCode.DELETED, likeResponse);
    }
}
