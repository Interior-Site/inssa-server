package com.inssa.server.api.review.like;

import com.inssa.server.api.review.like.dto.ReviewLikeCreateResponseDto;
import com.inssa.server.api.review.like.dto.ReviewLikeDeleteResponseDto;
import com.inssa.server.api.review.like.service.ReviewLikeService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;

    @Tag(name = "ordderReviewLike", description = "견적후기 공감 API")
    @Operation(description = "견적 후기 공감 추가")
    @PostMapping("/order/{orderReviewNo}/like")
    @PreAuthorizeLogInUser
    public InssaApiResponse<ReviewLikeCreateResponseDto> createOrderReviewLike(
        @PathVariable Long orderReviewNo,
        @AuthenticationPrincipal AuthUser user
    ){
        return null;
    }

    @Tag(name = "ordderReviewLike", description = "견적후기 공감 API")
    @Operation(description = "견적 후기 공감 취소")
    @DeleteMapping("/order/{orderReviewNo}/like/{likeNo}")
    @PreAuthorizeLogInUser
    public InssaApiResponse<ReviewLikeDeleteResponseDto> deleteOrderReviewLike(
            @PathVariable Long orderReviewNo,
            @PathVariable Long likeNo,
            @AuthenticationPrincipal AuthUser user
    ){
        return null;
    }

    @Tag(name = "buildReviewLike", description = "시공후기 공감 API")
    @Operation(description = "시공 후기 공감 추가")
    @PostMapping("/build/{buildReviewNo}/like")
    @PreAuthorizeLogInUser
    public InssaApiResponse<ReviewLikeCreateResponseDto> createBuildReviewLike(
            @PathVariable Long buildReviewNo,
            @AuthenticationPrincipal AuthUser user
    ){
        return null;
    }

    @Tag(name = "buildReviewLike", description = "시공후기 공감 API")
    @Operation(description = "시공 후기 공감 취소")
    @DeleteMapping("/build/{buildReviewNo}/like/{likeNo}")
    @PreAuthorizeLogInUser
    public InssaApiResponse<ReviewLikeDeleteResponseDto> deletebuildReviewLike(
            @PathVariable Long buildReviewNo,
            @PathVariable Long likeNo,
            @AuthenticationPrincipal AuthUser user
    ){
        return null;
    }

}
