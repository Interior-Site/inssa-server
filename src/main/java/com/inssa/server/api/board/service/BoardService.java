package com.inssa.server.api.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BoardService {

    public ApiResponse insertBoard(BoardDto board) {

        ApiResponse response = new ApiResponse();
        ObjectMapper objectMapper = new ObjectMapper();

        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        if(!board.getBoardStatus().equals("N")) {
            statusCode = StatusCode.SUCCESS;
            message = ResponseMessage.SUCCESS;
        }

        int boardNo = board.getBoardNo();
        board.setBoardNo(boardNo++);

        Map<String, Object> dataMap = objectMapper.convertValue(board, Map.class);

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
//        response.putData("Board",dataMap);

        return response;
    }

}
