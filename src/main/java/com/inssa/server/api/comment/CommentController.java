package com.inssa.server.api.comment;

import com.inssa.server.api.comment.dto.CommentDto;
import com.inssa.server.api.comment.service.CommentService;
import com.inssa.server.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 알아서 RequestBody에 해당하는 내용 json 포맷으로 반환
@RequestMapping("/api/v1/comment")
@Tag(name = "comment", description = "댓글 API") // 테스트 할 때
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    @Tag(name = "comment")
    @Operation(summary = "selectList", description = "댓글 목록 조회 API") // 스웨거
    @PostMapping("/selectList")
    public ApiResponse selectList(int boardNo){
        return commentService.selectList(boardNo);
    }

    @Operation(summary = "insertComment", description = "댓글 등록 API")
    @PostMapping("/insertComment")
    public ApiResponse insertComment(@RequestBody CommentDto comment){
        return commentService.insertComment(comment);
    }
    
    // 댓글 수정
    
    // 댓글 삭제

}
