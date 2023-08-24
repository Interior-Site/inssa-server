package com.inssa.server.api.review.like;

import com.inssa.server.api.review.like.dto.ReviewLikeCreateResponseDto;
import com.inssa.server.api.review.like.dto.ReviewLikeDeleteResponseDto;
import com.inssa.server.api.review.like.service.ReviewLikeService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;

    @Tag(name = "orderReviewLike", description = "견적후기 공감 API")
    @Operation(description = "견적 후기 공감 추가")
    @PostMapping("/order/{orderReviewNo}/like")
    @PreAuthorizeLogInUser
    public InssaApiResponse<ReviewLikeCreateResponseDto> createOrderReviewLike(
        @Parameter(description = "견적 후기 No") @PathVariable Long orderReviewNo,
        @AuthenticationPrincipal AuthUser user
    ){
        ReviewLikeCreateResponseDto response = reviewLikeService.createOrderReviewLike(orderReviewNo, user.getUserNo());
        return InssaApiResponse.ok(ResponseCode.CREATED, response);
    }

    @Tag(name = "orderReviewLike", description = "견적후기 공감 API")
    @Operation(description = "견적 후기 공감 취소")
    @DeleteMapping("/order/{orderReviewNo}/like")
    @PreAuthorizeLogInUser
    public InssaApiResponse<ReviewLikeDeleteResponseDto> deleteOrderReviewLike(
            @Parameter(description = "견적 후기 No") @PathVariable Long orderReviewNo,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewLikeDeleteResponseDto response = reviewLikeService.deleteOrderReviewLike(orderReviewNo, user.getUserNo());
        return InssaApiResponse.ok(ResponseCode.DELETED, response);
    }

    @Tag(name = "buildReviewLike", description = "시공후기 공감 API")
    @Operation(description = "시공 후기 공감 추가")
    @PostMapping("/build/{buildReviewNo}/like")
    @PreAuthorizeLogInUser
    public InssaApiResponse<ReviewLikeCreateResponseDto> createBuildReviewLike(
            @Parameter(description = "시공 후기 No") @PathVariable Long buildReviewNo,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewLikeCreateResponseDto response = reviewLikeService.createBuildReviewLike(buildReviewNo, user.getUserNo());
        return InssaApiResponse.ok(ResponseCode.CREATED, response);
    }

    @Tag(name = "buildReviewLike", description = "시공후기 공감 API")
    @Operation(description = "시공 후기 공감 취소")
    @DeleteMapping("/build/{buildReviewNo}/like")
    @PreAuthorizeLogInUser
    public InssaApiResponse<ReviewLikeDeleteResponseDto> deleteBuildReviewLike(
            @Parameter(description = "시공 후기 No") @PathVariable Long buildReviewNo,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewLikeDeleteResponseDto response = reviewLikeService.deleteBuildReviewLike(buildReviewNo, user.getUserNo());
        return InssaApiResponse.ok(ResponseCode.DELETED, response);
    }
}
