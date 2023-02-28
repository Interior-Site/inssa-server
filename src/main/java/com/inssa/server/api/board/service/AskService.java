package com.inssa.server.api.board.service;

import com.inssa.server.api.board.dao.AskDao;
import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.Pagination;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("AskService")
@RequiredArgsConstructor
public class AskService {

    private final AskDao dao;

    public ApiResponse insertBoard(BoardDto board) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int result = dao.insert(board);

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

        List<BoardDto> resultList = dao.selectBoardList();

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message = "게시글 전체 목록 조회 " + ResponseMessage.SUCCESS;
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    public ApiResponse selectBoardNo(int boardNo, HttpServletRequest request) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        BoardDto dto = new BoardDto();
        dto.setUrl(request.getRequestURL().toString());

        List<BoardDto> resultList = dao.selectBoard(boardNo);

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message = boardNo + " 번호의 게시글 조회 " + ResponseMessage.SUCCESS;

            // 조회 성공일 때마다 조회수 + 1
            dao.updateView(boardNo);
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    @Transactional
    public ApiResponse deleteBoard(int boardNo) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = dao.deleteBoard(boardNo);

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message = boardNo + " 번호의 게시글 삭제 " + ResponseMessage.SUCCESS;
        }

        return response;
    }

    @Transactional public ApiResponse updateBoard(BoardDto dto) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        int boardNo = dto.getBoardNo();
        List<BoardDto> resultList = dao.updateBoard(boardNo);

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message =  boardNo + " 번호의 게시글 수정 " +ResponseMessage.SUCCESS;
        }

        return response;
    }

    public ApiResponse searchBoardList(BoardDto dto) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = dao.searchBoardList(dto);

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message = dto.boardNo + " 번호의 게시글 검색 " +ResponseMessage.SUCCESS;
        }

        return response;
    }

    public Pagination getPaging(BoardDto board, Pagination page) {

        // 1) 검색이 적용된 게시글 수 조회
        Pagination selectPg = dao.searchListCount(board);

        //System.out.println(selectPg);

        // 2) 계산이 완료된 Pagination 객체 생성 후 반환
//        return new Pagination(pg.getCurrentPage(), selectPg.getListCount());

        return selectPg;
    }
}
