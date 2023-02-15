package com.inssa.server.api.comment;

import com.inssa.server.api.comment.dto.CommentDto;
import com.inssa.server.api.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@Tag(name = "comment", description = "댓글 API") // 테스트 할 때
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    @Tag(name = "comment")
    @Operation(summary = "selectList", description = "댓글 목록 조회 API") // 스웨거
    @PostMapping("/selectList")
    public List<CommentDto> selectList(int commentNo){
        List<CommentDto> cList = commentService.selectList(commentNo);
        return cList;
    }
}
