package com.inssa.server.api.comment;

import com.inssa.server.api.comment.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
@Tag(name = "user", description = "댓글 API") // 테스트 할 때
@RequiredArgsConstructor //
public class CommentController {

    private CommentService commentService;
}
