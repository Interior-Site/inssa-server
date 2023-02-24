package com.inssa.server.api.board.controller;


import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.dto.LikeDto;
import com.inssa.server.api.board.service.BoardService;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.Pagination;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/board")
@RestController
@RequiredArgsConstructor
@Api(tags="Board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    @PostMapping(value="/insert") @ApiOperation(value = "게시글 작성")
    public ApiResponse insertBoard(@RequestBody BoardDto board) {

        ApiResponse response = new ApiResponse();
        response = boardService.insertBoard(board);
        return response;
    }

    @GetMapping(value="/select") @ApiOperation(value="게시글 목록 조회")
    public ApiResponse selectBoardList(@RequestParam int boardNo) {

        ApiResponse response = new ApiResponse();
        response = boardService.selectBoard(boardNo);
        return response;
    }

    @GetMapping(value="/select/{boardNo}") @ApiOperation(value="게시글 상세 조회")
    public ApiResponse selectBoardContent(@PathVariable int boardNo) {

        ApiResponse response = new ApiResponse();
        response = boardService.selectBoardNo(boardNo);
        return response;
    }

    @GetMapping(value="/delete/{boardNo}") @ApiOperation(value="게시글삭제")
    public ApiResponse deleteBoard(@RequestParam int boardNo) {

        ApiResponse response = new ApiResponse();
        response = boardService.deleteBoard(boardNo);
        return response;
    }

    @GetMapping(value="/update/{boardNo}") @ApiOperation(value="게시글수정")
    public ApiResponse updateBoard(@RequestParam int boardNo) {

        ApiResponse response = new ApiResponse();
        response = boardService.updateBoard(boardNo);
        return response;
    }

    @GetMapping(value="/select/searchBoard") @ApiOperation(value="게시글 검색")
    public ApiResponse searchBoardList(@RequestParam("filter") String filter, @RequestParam("searchWord") String searchWord,
                                       @RequestParam("category") String category,Pagination page) {
        ApiResponse response = new ApiResponse();

        BoardDto dto = new BoardDto();
        dto.setFilter(filter);
        dto.setSearchWord(searchWord);
        dto.setCategory(category);

        Pagination paging = new Pagination();
        paging.setCurrentPage(page.getCurrentPage());

        if(searchWord != null) {
            paging = boardService.getPaging(dto, paging);
            response = boardService.searchBoardList(dto);
        } else {

        }
        return response;

    }

    @PostMapping(value="/updateLike") @ApiOperation(value="게시글 좋아요 ")
    public ApiResponse updateLike(@RequestBody LikeDto like) {
        ApiResponse response = new ApiResponse();

        BoardDto board = new BoardDto();
        board.setBoardNo(like.getBoardNo());
        board.setUserNo(like.getUserNo());
        board.setBoardLike(like.getLike());

        String likeYn = like.getLike();

        if(likeYn.equals("Y") || likeYn.equals("N") ) {
            response = boardService.updateLike(board);
        } else {
            response.setResponseMessage(ResponseMessage.FAIL);
            response.setStatusCode(StatusCode.FAIL);
            response.putData("result","");
        }

        return response;
    }

}

