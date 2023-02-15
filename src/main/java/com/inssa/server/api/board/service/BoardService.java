package com.inssa.server.api.board.service;

import com.inssa.server.api.board.dao.BoardDao;
import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service("BoardService")
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;

    public ApiResponse insertBoard(BoardDto board) {

        ApiResponse response = new ApiResponse();

        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = boardDao.insert(board);

        if(result != 0) {
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

}
