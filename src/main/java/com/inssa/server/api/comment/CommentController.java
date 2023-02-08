package com.inssa.server.api.comment;

import com.inssa.server.api.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor //
public class CommentController {

    private CommentService commentService;
}
