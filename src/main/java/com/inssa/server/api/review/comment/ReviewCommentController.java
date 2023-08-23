package com.inssa.server.api.review.comment;

import com.inssa.server.api.review.comment.dto.ReviewCommentCreateResponseDto;
import com.inssa.server.api.review.comment.dto.ReviewCommentListResponseDto;
import com.inssa.server.api.review.comment.dto.ReviewCommentRequestDto;
import com.inssa.server.api.review.comment.service.ReviewCommentService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "reviewComment", description = "후기 댓글 API")
@RequestMapping("/api/v1/review")
@RestController
public class ReviewCommentController {

    private final ReviewCommentService reviewCommentService;

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    )
            }
    )
    @Parameter(in = ParameterIn.QUERY
            , description = "페이지 (0 부터 시작)"
            , name = "page"
            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY
            , description = "페이지당 데이터 수"
            , name = "size"
            , content = @Content(schema = @Schema(type = "integer", defaultValue = "10")))
    @Operation(summary = "견적후기 댓글 목록 조회 API", tags = "reviewComment")
    @GetMapping("/order/{reviewNo}/comments")
    public InssaApiResponse<Page<ReviewCommentListResponseDto>> findOrderReviewComments(
            @PathVariable Long reviewNo,
            @ParameterObject @Valid @PageableDefault Pageable pageable
    ){
        return InssaApiResponse.ok(reviewCommentService.findOrderReviewCommentsByReviewId(reviewNo, pageable));
    }


    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    )
            }
    )
    @Parameter(in = ParameterIn.QUERY
            , description = "페이지 (0 부터 시작)"
            , name = "page"
            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY
            , description = "페이지당 데이터 수"
            , name = "size"
            , content = @Content(schema = @Schema(type = "integer", defaultValue = "10")))
    @Operation(summary = "시공후기 댓글 목록 조회 API", tags = "reviewComment")
    @GetMapping("/build/{reviewNo}/comments")
    public InssaApiResponse<Page<ReviewCommentListResponseDto>> findBuildReviewComments(
            @PathVariable Long reviewNo,
            @ParameterObject @Valid @PageableDefault Pageable pageable
    ){
        return InssaApiResponse.ok(reviewCommentService.findBuildReviewCommentsByReviewId(reviewNo, pageable));
    }


    @Operation(summary = "견적후기 댓글 등록 API", tags = "reviewComment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "등록 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "CREATED",
                                    value = """
                                            {
                                                    "message":{
                                                        "code":201,
                                                        "message":"CREATED"
                                                    },
                                                    "result": {
                                                        "orderReviewCommentNo": 3
                                                    }
                                                }
                                            """
                            )
                    )
            )
    })
    @PreAuthorizeLogInUser
    @PostMapping("/order/{reviewNo}/comment")
    public InssaApiResponse<ReviewCommentCreateResponseDto> createOrderReviewComment(
            @PathVariable Long reviewNo,
            @RequestBody @Valid ReviewCommentRequestDto request,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewCommentCreateResponseDto response = reviewCommentService.createOrderReviewComment(reviewNo, request, user.getUserNo());
        return InssaApiResponse.ok(ResponseCode.CREATED, response);
    }

}
