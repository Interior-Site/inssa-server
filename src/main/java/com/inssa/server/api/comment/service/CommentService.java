package com.inssa.server.api.comment.service;

import com.inssa.server.api.comment.dao.CommentDao;
import com.inssa.server.api.comment.dto.CommentDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CommentService")
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentdao;

    public List<CommentDto> selectList(int boardNo){
        return commentdao.selectList(boardNo);
    }

    public ApiResponse insertComment(CommentDto comment) {
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = commentdao.insertComment(comment);

        // 댓글 등록에 성공했다면
        if(result > 0){
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
            response.putData("userId", comment);
        }

        // 응답에 결과코드, 메세지 담음
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }
}
