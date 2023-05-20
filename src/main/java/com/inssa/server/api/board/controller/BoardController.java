package com.inssa.server.api.board.controller;

import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.dto.LikeDto;
import com.inssa.server.api.board.service.BoardService;

import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.Pagination;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public class BoardController {

//    @Autowired
//    private UploadFile uploadFile;
    private BoardService boardService;


//    @PostMapping(value="/insert") @ApiOperation(value = "게시글 작성")
//    public ApiResponse insertBoard(@RequestBody BoardDto board, List<MultipartFile> files) throws IOException {
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
//        String filePath = "resources/board/images";
//        response = askService.insertBoard(board, files, filePath);
//
//        return response;
//    }

    @GetMapping(value="/select") @ApiOperation(value="게시글 목록 조회")
    public ApiResponse selectBoardList(@RequestParam int boardTypeNo) {

        ApiResponse response = new ApiResponse();
        response = boardService.selectBoard(boardTypeNo);
        return response;
    }

    @GetMapping(value="/select/{boardNo}") @ApiOperation(value="게시글 상세 조회")
    public ApiResponse selectBoardContent(@PathVariable int boardNo, @RequestParam int boardTypeNo, @RequestParam String userId, HttpServletRequest request) {

        ApiResponse response = new ApiResponse();
        response = boardService.selectBoardNo(boardNo, boardTypeNo,  userId, request);
        return response;
    }

    @GetMapping(value="/delete/{boardNo}") @ApiOperation(value="게시글삭제")
    public ApiResponse deleteBoard(@RequestParam int boardNo, @RequestParam String userId) {

        ApiResponse response = new ApiResponse();
        response = boardService.deleteBoard(boardNo, userId);
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
//        response = askService.updateBoard(board);
//        return response;
//    }

    @GetMapping(value="/select/{}{categoryNo}") @ApiOperation(value="게시글 검색")
    public ApiResponse searchBoardList(@RequestParam("filter") String filter, @RequestParam("searchWord") String searchWord, @PathVariable int categoryNo, @PathVariable int boardTypeNo, Pagination page, @RequestParam(value="cp", required=false, defaultValue = "1" ) int currentPg) {
        ApiResponse response = new ApiResponse();

        BoardDto dto = new BoardDto();
        dto.setFilter(filter);
        dto.setSearchWord(searchWord);
        dto.setCategory(String.valueOf(categoryNo));

        Pagination paging = new Pagination();
        paging.setCurrentPage(currentPg);
        paging.setBoardTypeNo(boardTypeNo);

        if(searchWord != null) {
            paging = boardService.getPaging(dto, paging);
            response = boardService.searchBoardList(dto, paging);
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
        board.setLikeNo(like.getLikeNo());

        response = boardService.updateLike(board);

        return response;
    }

}
