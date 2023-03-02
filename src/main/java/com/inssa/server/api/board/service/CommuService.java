package com.inssa.server.api.board.service;

import com.inssa.server.api.board.dao.CommuDao;
import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.user.dao.UserDao;
import com.inssa.server.api.user.dto.UserDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.Pagination;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service("BoardService")
@RequiredArgsConstructor
public class CommuService {

    private final CommuDao boardDao;
    private final UserDao userDao;

    // 게시글 작성 (-> 회원만 작성 가능)
    public ApiResponse insertBoard(BoardDto board) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        // 회원 or 비회원 체크
        String userId = board.getUserId();
        UserDto user = userDao.findByUserId(userId);

        if(user != null) { // 회원일 경우
            int result = boardDao.insert(board);

            if(result != 0) {
                statusCode = StatusCode.SUCCESS;
                message = "게시글 작성 " + ResponseMessage.SUCCESS;
                response.putData(String.valueOf(board.getBoardNo()), board);
            }
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("Board", board.toString());
        return response;
    }

    // 게시글 전체 목록 조회 (-> 전체회원(회원/비회원) 조회 가능)
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

    // 게시글 상세 조회 (-> 회원만 조회 가능)
    public ApiResponse selectBoardNo(int boardNo, String userId, HttpServletRequest request) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = new ArrayList<>();

        BoardDto dto = new BoardDto();
        dto.setUrl(request.getRequestURL().toString());

        // 회원 or 비회원 체크
        UserDto user = userDao.findByUserId(userId);

        if(user != null) { // 회원일 경우
            resultList = boardDao.selectBoard(boardNo);

            if(!resultList.isEmpty()) {
                statusCode = StatusCode.SUCCESS;
                message = boardNo + " 번호의 게시글 조회 " + ResponseMessage.SUCCESS;

                // 조회 성공일 때마다 조회수 + 1
                boardDao.updateView(boardNo);
            }
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    // 게시글 삭제 (-> 회원만 '본인'이 작성한 게시글에 한하여 가능)
    @Transactional public ApiResponse deleteBoard(int boardNo, String userId) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = new ArrayList<>();

        // 회원 or 비회원 체크
        UserDto user = userDao.findByUserId(userId);

        if(user != null) { // 회원일 경우
            resultList = boardDao.deleteBoard(boardNo);

            if(!resultList.isEmpty()) {
                statusCode = StatusCode.SUCCESS;
                message = boardNo + " 번호의 게시글 삭제 " + ResponseMessage.SUCCESS;
            }

        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    // 게시글 수정 (-> 회원만 '본인'이 작성한 게시글에 한하여 가능)
    @Transactional public ApiResponse updateBoard(BoardDto dto) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = new ArrayList<>();

        // 회원 or 비회원 체크
        String userId = dto.getUserId();
        UserDto user = userDao.findByUserId(userId);

        if(user != null) { //회원일 경우
            int boardNo = dto.getBoardNo();
            resultList = boardDao.updateBoard(boardNo);

            if(!resultList.isEmpty()) {
                statusCode = StatusCode.SUCCESS;
                message =  boardNo + " 번호의 게시글 수정 " +ResponseMessage.SUCCESS;
            }
        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    // 게시글 검색 (-> 전체회원(회원/비회원) 모두 가능)
    public ApiResponse searchBoardList(BoardDto dto) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = boardDao.searchBoardList(dto);

        if(!resultList.isEmpty()) {
            statusCode = StatusCode.SUCCESS;
            message = dto.boardNo + " 번호의 게시글 검색 " +ResponseMessage.SUCCESS;
        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("selectBoard",resultList);
        return response;
    }

    // 게시판 페이징 처리를 위한 기능
    public Pagination getPaging(BoardDto board, Pagination page) {

        // 1) 검색이 적용된 게시글 수 조회
        Pagination selectPg = boardDao.searchListCount(board);

        //System.out.println(selectPg);

        // 2) 계산이 완료된 Pagination 객체 생성 후 반환
//        return new Pagination(pg.getCurrentPage(), selectPg.getListCount());

        return selectPg;
    }

}
