package com.inssa.server.api.board.comment.comment;

import com.inssa.server.api.board.comment.comment.dto.*;
import com.inssa.server.api.board.comment.comment.service.CommentService;
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
@Tag(name = "comment", description = "댓글 API")
@RequestMapping("/api/v1/posts/{postNo}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 등록 API", tags = "comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
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
    @PostMapping
    public InssaApiResponse<CommentNoResponseDto> createComment(
            @PathVariable Long postNo,
            @RequestBody @Valid final CommentCreateRequestDto createRequest,
            @AuthenticationPrincipal AuthUser user
    ){
        CommentRequestDto request = CommentRequestDto.createBuilder()
                .postNo(postNo)
                .parentNo(createRequest.getParentNo())
                .content(createRequest.getContent())
                .userNo(user.getUserNo())
                .build();
        CommentNoResponseDto response = commentService.createComment(request);
        return InssaApiResponse.success(ResponseCode.CREATED, response);
    }


    @Operation(summary = "댓글 목록 조회 API", tags = "comment")
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
    @GetMapping
    public InssaApiResponse<Page<CommentListResponseDto>> findComments(
            @PathVariable Long postNo,
            @Valid @ParameterObject @PageableDefault(sort = "no", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal AuthUser user
    ){
        Long userNo = Objects.nonNull(user)? user.getUserNo(): null;
        return InssaApiResponse.success(commentService.findCommentsByNo(postNo, pageable, userNo));
    }


    @Operation(summary = "댓글 수정 API", tags = "comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
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
    @PutMapping
    public InssaApiResponse<CommentNoResponseDto> updateComment(
            @PathVariable Long postNo,
            @RequestBody @Valid final CommentUpdateRequestDto updateRequest,
            @AuthenticationPrincipal AuthUser user
    ){
        CommentRequestDto request = CommentRequestDto.updateBuilder()
                .postNo(postNo)
                .commentNo(updateRequest.getCommentNo())
                .content(updateRequest.getContent())
                .userNo(user.getUserNo())
                .build();
        CommentNoResponseDto response = commentService.updateComment(request);
        return InssaApiResponse.success(ResponseCode.UPDATED, response);
    }


    @Operation(summary = "댓글 삭제 API", tags = "comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "203",
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
    @DeleteMapping("/{commentNo}")
    public InssaApiResponse<CommentNoResponseDto> deleteComment(
            @PathVariable Long postNo,
            @PathVariable Long commentNo,
            @AuthenticationPrincipal AuthUser user
    ){
        CommentRequestDto request = CommentRequestDto.deleteBuilder()
                .postNo(postNo)
                .commentNo(commentNo)
                .userNo(user.getUserNo())
                .build();
        CommentNoResponseDto response = commentService.deleteComment(request);
        return InssaApiResponse.success(ResponseCode.DELETED, response);
    }
}
