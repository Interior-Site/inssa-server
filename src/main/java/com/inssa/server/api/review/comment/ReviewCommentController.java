package com.inssa.server.api.review.comment;

import com.inssa.server.api.review.comment.dto.*;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "reviewComment", description = "후기 댓글 API")
@RequestMapping("/api/v1/review")
@RestController
public class ReviewCommentController {

    private final ReviewCommentService reviewCommentService;


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
                                                        "commentNo": 3
                                                    }
                                                }
                                            """
                            )
                    )
            )
    })
    @PreAuthorizeLogInUser
    @PostMapping("/order/{reviewNo}/comment")
    public InssaApiResponse<ReviewCommentResponseDto> createOrderReviewComment(
            @PathVariable Long reviewNo,
            @RequestBody @Valid final ReviewCommentCreateRequestDto createRequest,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewCommentRequestDto request = ReviewCommentRequestDto.createBuilder()
                .reviewNo(reviewNo)
                .parentNo(createRequest.getParentNo())
                .content(createRequest.getContent())
                .userNo(user.getUserNo())
                .build();
        ReviewCommentResponseDto response = reviewCommentService.createOrderReviewComment(request);
        return InssaApiResponse.ok(ResponseCode.CREATED, response);
    }

    @Operation(summary = "시공후기 댓글 등록 API", tags = "reviewComment")
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
                                                        "commentNo": 3
                                                    }
                                                }
                                            """
                            )
                    )
            )
    })
    @PreAuthorizeLogInUser
    @PostMapping("/build/{reviewNo}/comment")
    public InssaApiResponse<ReviewCommentResponseDto> createBuildReviewComment(
            @PathVariable Long reviewNo,
            @RequestBody @Valid final ReviewCommentCreateRequestDto createRequest,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewCommentRequestDto request = ReviewCommentRequestDto.createBuilder()
                .reviewNo(reviewNo)
                .parentNo(createRequest.getParentNo())
                .content(createRequest.getContent())
                .userNo(user.getUserNo())
                .build();
        ReviewCommentResponseDto response = reviewCommentService.createBuildReviewComment(request);
        return InssaApiResponse.ok(ResponseCode.CREATED, response);
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
    @Operation(summary = "견적후기 댓글 목록 조회 API", tags = "reviewComment")
    @GetMapping("/order/{reviewNo}/comments")
    public InssaApiResponse<Page<ReviewCommentListResponseDto>> findOrderReviewComments(
            @PathVariable Long reviewNo,
            @Valid @ParameterObject @PageableDefault(size = 10, page = 0, sort = "no", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal AuthUser user
    ){
        Long userNo = Objects.nonNull(user)? user.getUserNo(): null;
        return InssaApiResponse.ok(reviewCommentService.findOrderReviewCommentsByReviewId(reviewNo, pageable, userNo));
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
            @Valid @ParameterObject @PageableDefault(size = 10, page = 0, sort = "no", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal AuthUser user
    ){
        Long userNo = Objects.nonNull(user)? user.getUserNo(): null;
        return InssaApiResponse.ok(reviewCommentService.findBuildReviewCommentsByReviewId(reviewNo, pageable, userNo));
    }


    @Operation(summary = "견적후기 댓글 수정 API", tags = "reviewComment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "수정 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "UPDATED",
                                    value = """
                                            {
                                                    "message":{
                                                        "code":202,
                                                        "message":"UPDATED"
                                                    },
                                                    "result": {
                                                        "commentNo": 3
                                                    }
                                                }
                                            """
                            )
                    )
            )
    })
    @PreAuthorizeLogInUser
    @PutMapping("/order/{reviewNo}/comment")
    public InssaApiResponse<ReviewCommentResponseDto> updateOrderReviewComment(
            @PathVariable Long reviewNo,
            @RequestBody @Valid final ReviewCommentUpdateRequestDto updateRequest,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewCommentRequestDto request = ReviewCommentRequestDto.updateBuilder()
                .reviewNo(reviewNo)
                .commentNo(updateRequest.getCommentNo())
                .content(updateRequest.getContent())
                .userNo(user.getUserNo())
                .build();
        ReviewCommentResponseDto response = reviewCommentService.updateOrderReviewComment(request);
        return InssaApiResponse.ok(ResponseCode.UPDATED, response);
    }

    @Operation(summary = "시공후기 댓글 수정 API", tags = "reviewComment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "수정 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "UPDATED",
                                    value = """
                                            {
                                                    "message":{
                                                        "code":202,
                                                        "message":"UPDATED"
                                                    },
                                                    "result": {
                                                        "commentNo": 3
                                                    }
                                                }
                                            """
                            )
                    )
            )
    })
    @PreAuthorizeLogInUser
    @PutMapping("/build/{reviewNo}/comment")
    public InssaApiResponse<ReviewCommentResponseDto> updateBuildReviewComment(
        @PathVariable Long reviewNo,
        @RequestBody @Valid final ReviewCommentUpdateRequestDto updateRequest,
        @AuthenticationPrincipal AuthUser user
    ){
     ReviewCommentRequestDto request = ReviewCommentRequestDto.updateBuilder()
             .reviewNo(reviewNo)
             .commentNo(updateRequest.getCommentNo())
             .content(updateRequest.getContent())
             .userNo(user.getUserNo())
             .build();
     ReviewCommentResponseDto response = reviewCommentService.updateBuildReviewComment(request);
     return InssaApiResponse.ok(ResponseCode.UPDATED, response);
    }

    @Operation(summary = "견적후기 댓글 삭제 API", tags = "reviewComment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "삭제 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "DELETED",
                                    value = """
                                            {
                                                    "message":{
                                                        "code":203,
                                                        "message":"DELETED"
                                                    },
                                                    "result": {
                                                        "commentNo": 3
                                                    }
                                                }
                                            """
                            )
                    )
            )
    })
    @PreAuthorizeLogInUser
    @PutMapping("/order/{reviewNo}/comment/{commentNo}")
    public InssaApiResponse<ReviewCommentResponseDto> deleteOrderReviewComment(
            @PathVariable Long reviewNo,
            @PathVariable Long commentNo,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewCommentRequestDto request = ReviewCommentRequestDto.deleteBuilder()
                .reviewNo(reviewNo)
                .commentNo(commentNo)
                .userNo(user.getUserNo())
                .build();
        ReviewCommentResponseDto response = reviewCommentService.deleteOrderReviewComment(request);
        return InssaApiResponse.ok(ResponseCode.DELETED, response);
    }


    @Operation(summary = "시공후기 댓글 삭제 API", tags = "reviewComment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "삭제 성공",
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "DELETED",
                                    value = """
                                            {
                                                    "message":{
                                                        "code":203,
                                                        "message":"DELETED"
                                                    },
                                                    "result": {
                                                        "commentNo": 3
                                                    }
                                                }
                                            """
                            )
                    )
            )
    })
    @PreAuthorizeLogInUser
    @PutMapping("/build/{reviewNo}/comment/{commentNo}")
    public InssaApiResponse<ReviewCommentResponseDto> deleteBuildReviewComment(
            @PathVariable Long reviewNo,
            @PathVariable Long commentNo,
            @AuthenticationPrincipal AuthUser user
    ){
        ReviewCommentRequestDto request = ReviewCommentRequestDto.deleteBuilder()
                .reviewNo(reviewNo)
                .commentNo(commentNo)
                .userNo(user.getUserNo())
                .build();
        ReviewCommentResponseDto response = reviewCommentService.deleteBuildReviewComment(request);
        return InssaApiResponse.ok(ResponseCode.DELETED, response);
    }
}
