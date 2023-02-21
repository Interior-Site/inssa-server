package com.inssa.server.api.board.service;

import com.inssa.server.api.board.dao.BoardDao;
import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
            message = "게시글 작성 " + ResponseMessage.SUCCESS;
            response.putData(String.valueOf(board.getBoardNo()), board);
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);

        return response;
    }

    public ApiResponse selectBoard(int boardNo) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = boardDao.selectBoardList();

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message = "게시글 전체 목록 조회 " + ResponseMessage.SUCCESS;
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    public ApiResponse selectBoardNo(int boardNo) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = boardDao.selectBoard(boardNo);

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message = boardNo + " 번호의 게시글 조회 " + ResponseMessage.SUCCESS;
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    @Transactional public ApiResponse deleteBoard(int boardNo) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = boardDao.deleteBoard(boardNo);

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message = boardNo + " 번호의 게시글 삭제 " + ResponseMessage.SUCCESS;
        }

        return response;
    }

    @Transactional public ApiResponse updateBoard(int boardNo) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = boardDao.updateBoard(boardNo);

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message =  boardNo + " 번호의 게시글 수정 " +ResponseMessage.SUCCESS;
        }

        return response;
    }

}
