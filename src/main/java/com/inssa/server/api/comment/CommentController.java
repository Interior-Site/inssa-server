package com.inssa.server.api.comment;

import com.inssa.server.api.comment.dto.CommentDto;
import com.inssa.server.api.comment.service.CommentService;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // 알아서 RequestBody에 해당하는 내용 json 포맷으로 반환
@RequestMapping("/api/v1/comment")
@Tag(name = "comment", description = "댓글 API") // 테스트 할 때
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 목록 조회 API", tags = "comment") // 스웨거
    @GetMapping("/selectList")
    //@GetMapping(value = "/selectList/{boardNo}")
    public InssaApiResponse<List<CommentDto>> selectList(@RequestParam int boardNo){
        return InssaApiResponse.ok(commentService.selectList(boardNo));
    }

    @Operation(summary = "댓글 등록 API", tags = "comment")
    @PostMapping("/insertComment")
    public InssaApiResponse<Object> insertComment(@RequestBody CommentDto comment){
        commentService.insertComment(comment);
        return InssaApiResponse.ok(ResponseCode.CREATED);
    }


    // 댓글 수정
    @Operation(summary = "댓글 수정 API", tags = "comment")
    @PostMapping("/updateComment")
    public InssaApiResponse<Object> updateComment(@RequestBody CommentDto comment){
        commentService.updateComment(comment);
        return InssaApiResponse.ok(ResponseCode.UPDATED);
    }

    // 댓글 삭제
    @Operation(summary = "댓글 삭제 API", tags = "comment")
    @PostMapping(value="/deleteComment")
    public InssaApiResponse<Object> deleteComment(@RequestBody CommentDto comment){
        commentService.deleteComment(comment);
        return InssaApiResponse.ok(ResponseCode.DELETED);
    }


}
