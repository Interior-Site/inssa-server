package com.inssa.server.api.board.controller;

import com.inssa.server.api.board.UploadFile;
import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.dto.LikeDto;
import com.inssa.server.api.board.dto.StarDto;
import com.inssa.server.api.board.dto.ZzimDto;
import com.inssa.server.api.board.service.ReviewService;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.Pagination;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("/api/v1/board/review")
@RestController
@RequiredArgsConstructor
@Api(tags="Review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

//    @Autowired
//    private UploadFile uploadFile;


//    @PostMapping(value="/insert") @ApiOperation(value = "게시글 작성")
//    public ApiResponse insertBoard(@RequestBody BoardDto board, MultipartFile files) throws IOException {
//
//        ApiResponse response = new ApiResponse();
//
//        if(files.isEmpty()) {
//            String img = "";
//            board.setBoardImg(img);
//        } else {
//            String img = uploadFile.fileUpload(files);
//            board.setBoardImg(img);
//        }
//
//        response = reviewService.insertBoard(board);
//
//        return response;
//    }

    @GetMapping(value="/select") @ApiOperation(value="게시글 목록 조회")
    public ApiResponse selectBoardList(@RequestParam int boardNo) {

        ApiResponse response = new ApiResponse();
        response = reviewService.selectBoard(boardNo);
        return response;
    }

    @GetMapping(value="/select/{boardNo}") @ApiOperation(value="게시글 상세 조회")
    public ApiResponse selectBoardContent(@PathVariable int boardNo, @RequestParam String userId, HttpServletRequest request) {

        ApiResponse response = new ApiResponse();
        response = reviewService.selectBoardNo(boardNo, userId, request);
        return response;
    }

    @GetMapping(value="/delete/{boardNo}") @ApiOperation(value="게시글삭제")
    public ApiResponse deleteBoard(@RequestParam int boardNo, @RequestParam String userId) {

        ApiResponse response = new ApiResponse();
        response = reviewService.deleteBoard(boardNo, userId);
        return response;
    }

//    @GetMapping(value="/update/{boardNo}") @ApiOperation(value="게시글수정")
//    public ApiResponse updateBoard(@RequestParam BoardDto board, MultipartFile files) throws IOException {
//
//        ApiResponse response = new ApiResponse();
//
//        if(!files.isEmpty()) {
//            String img = uploadFile.fileUpload(files);
//            board.setBoardImg(img);
//        }
//
//        response = reviewService.updateBoard(board);
//        return response;
//    }

    @GetMapping(value="/select/searchBoard") @ApiOperation(value="게시글 검색")
    public ApiResponse searchBoardList(@RequestParam("filter") String filter, @RequestParam("searchWord") String searchWord,
                                       @RequestParam("category") String category, Pagination page) {
        ApiResponse response = new ApiResponse();

        BoardDto dto = new BoardDto();
        dto.setFilter(filter);
        dto.setSearchWord(searchWord);
        dto.setCategory(category);

        Pagination paging = new Pagination();
        paging.setCurrentPage(page.getCurrentPage());

        if(searchWord != null) {
            paging = reviewService.getPaging(dto, paging);
            response = reviewService.searchBoardList(dto);
        } else {

        }
        return response;

    }

    @PostMapping(value="/updateLike") @ApiOperation(value="게시글 좋아요 ")
    public ApiResponse updateLike(@RequestBody LikeDto like) {
        ApiResponse response = new ApiResponse();

        BoardDto board = new BoardDto();
        board.setBoardNo(like.getBoardNo());
        board.setUserId(like.getUserId());
        board.setBoardLike(like.getLike());

        String likeYn = like.getLike();

        if(likeYn.equals("Y") || likeYn.equals("N") ) {
            response = reviewService.updateLike(board);
        } else {
            response.setResponseMessage(ResponseMessage.FAIL);
            response.setStatusCode(StatusCode.FAIL);
            response.putData("result","");
        }

        return response;
    }

    @PostMapping(value="/updateZzim") @ApiOperation(value="게시글 찜하기")
    public ApiResponse updateZzim(@RequestBody ZzimDto zzim) {
        ApiResponse response = new ApiResponse();

        BoardDto board = new BoardDto();
        board.setBoardNo(zzim.getBoardNo());
        board.setUserId(zzim.getUserId());
        board.setBoardZzim(zzim.getZzim());

        String ZzimYn = zzim.getZzim();

        if(ZzimYn.equals("Y") || ZzimYn.equals("N") ) {
            response = reviewService.updateZzim(board);
        } else {
            response.setResponseMessage(ResponseMessage.FAIL);
            response.setStatusCode(StatusCode.FAIL);
            response.putData("result","");
        }

        return response;
    }

    public ApiResponse updateStar(@RequestBody StarDto star) {
        ApiResponse response = new ApiResponse();

        BoardDto board = new BoardDto();
        board.setBoardNo(star.getBoardNo());
        board.setUserId(star.getUserId());
        board.setBoardGubun(star.getBoardGubun());

        if(star.getBoardGubun().equals("R")) {
            response = reviewService.updateStar(board);
        } else {  // 후기 게시판이 아닐 경우
            response.setStatusCode(StatusCode.FAIL);
            response.setResponseMessage(ResponseMessage.FAIL);
        }

        return response;
    }


}
