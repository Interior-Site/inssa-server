package com.inssa.server.api.board.service;

import com.inssa.server.api.board.dao.BoardDao;
import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.dto.LikeDto;
import com.inssa.server.api.user.data.UserRepository;
import com.inssa.server.common.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("AskService")
@RequiredArgsConstructor
public class BoardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BoardDao boardDao;

    private final UserRepository userRepository;

    // 게시글 작성 (-> 회원만 작성 가능)
    public ApiResponse insertBoard(BoardDto board, List<MultipartFile> boardImg, String filePath) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        // 회원 or 비회원 체크
        String userId = board.getUserId();

        List<Files> fileList = new ArrayList<Files>();

        if(userRepository.existsByUserId(userId)) { // 회원일 경우

            for(int i=0; i<boardImg.size(); i++) {

                if(!boardImg.get(i).getOriginalFilename().equals("")) {
                    String fileName = rename(boardImg.get(i).getOriginalFilename() );

                    Files files = new Files();
                    files.setBoardNo(board.getBoardNo());
                    files.setFilePath(filePath);
                    files.setUploadName(fileName);

                    fileList.add(files);
                }
            }

            if(!fileList.isEmpty()) { // 이미 업로드된 이미지가 있을 때

                int result = boardDao.insertImg(fileList);

                if(fileList.size() == result) {

                    // 4) 파일을 서버에 저장(transfer() )
                    for(int i=0; i<fileList.size(); i++) {
                        try {
                            boardImg.get( fileList.get(i).getFileLevel() )
                                    .transferTo(new File(filePath + "/" + fileList.get(i).getStoreName() ));
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

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
        response.putData("file", fileList);
        return response;
    }

    // 게시글 전체 목록 조회 (-> 전체회원(회원/비회원) 조회 가능)
    public ApiResponse selectBoard(int boardTypeNo) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = boardDao.selectBoardList(boardTypeNo);

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
    public ApiResponse selectBoardNo(int boardNo, int boardTypeNo, String userId, HttpServletRequest request) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = new ArrayList<>();

        BoardDto dto = new BoardDto();
        dto.setUrl(request.getRequestURL().toString());

        // 회원 or 비회원 체크

        if(userRepository.existsByUserId(userId)) { // 회원일 경우
            resultList = boardDao.selectBoard(boardNo);

            if(!resultList.isEmpty()) {
                statusCode = StatusCode.SUCCESS;
                message = boardNo + " 번호의 게시글 조회 " + ResponseMessage.SUCCESS;

                // 조회 성공일 때마다 조회수 + 1
                boardDao.updateView(boardNo, boardTypeNo);
            }
        } else { // 회원이 아닐경우
            statusCode = 200;
            message = null;
            resultList = null;
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    // 게시글 삭제 (-> 회원만 '본인'이 작성한 게시글에 한하여 가능)
    @Transactional
    public ApiResponse deleteBoard(int boardNo, String userId) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = new ArrayList<>();

        // 회원 or 비회원 체크

        if(userRepository.existsByUserId(userId)) { // 회원일 경우
            resultList = boardDao.deleteBoard(boardNo);

            if(!resultList.isEmpty()) {
                statusCode = StatusCode.SUCCESS;
                message = boardNo + " 번호의 게시글 삭제 " + ResponseMessage.SUCCESS;
            }
        } else { // 회원이 아닐경우
            statusCode = 200;
            message = null;
            resultList = null;
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

        if(userRepository.existsByUserId(userId)) { //회원일 경우
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
    public ApiResponse searchBoardList(BoardDto dto, Pagination page) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BoardDto> resultList = boardDao.searchBoardList(dto, page);

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
        return new Pagination(page.getCurrentPage(), selectPg.getListCount(), page.getBoardTypeNo());
    }

    // 게시판 좋아요 기능(-> 회원만 해당 기능 실행 가능)
    @Transactional public ApiResponse updateLike(BoardDto board) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;
        List<BoardDto> resultList = new ArrayList<>();

        // 회원 or 비회원 체크
        String userId = board.getUserId();

        if(userRepository.existsByUserId(userId)) { // 회원일 경우

            // 좋아요 여부 확인
            LikeDto like = boardDao.likeCheck(board.getLikeNo());

            if(like == null) { // 좋아요 기능을 실행했을 경우
                board.setLikeCount(1);
            } else { // 좋아요를 취소했을 경우
                board.setLikeCount(0);
            }

            resultList = boardDao.updateLike(board);

            if(!resultList.isEmpty()) {
                statusCode = StatusCode.SUCCESS;
                message = board.boardNo + " 번호의 게시글 좋아요 " +ResponseMessage.SUCCESS;
            }
        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    // 게시판 찜하기 기능(-> 회원만 해당 기능 실행 가능)
    @Transactional public ApiResponse updateZzim(BoardDto board) {

        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;
        List<BoardDto> resultList = new ArrayList<>();

        // 회원 or 비회원 체크
        String userId = board.getUserId();

        if(userRepository.existsByUserId(userId)) { // 회원일 경우
            resultList = boardDao.updateZzim(board);

            if(!resultList.isEmpty()) {
                statusCode = StatusCode.SUCCESS;
                message = board.boardNo + " 번호의 게시글 좋아요 " +ResponseMessage.SUCCESS;
            }
        }
        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("boardList",resultList);
        return response;
    }

    // 파일명 변경 메소드
    private String rename(String originFileName) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new java.util.Date(System.currentTimeMillis()));

        int ranNum = (int)(Math.random()*100000); // 5자리 랜덤 숫자 생성

        String str = "_" + String.format("%05d", ranNum);

        String ext = originFileName.substring(originFileName.lastIndexOf("."));

        return date + str + ext;
    }
}
