package com.inssa.server.api.board.controller;


import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.service.BoardService;
import com.inssa.server.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/board")
@RestController
@RequiredArgsConstructor
@Api(tags="게시판")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    @GetMapping(value="/insert") @ApiOperation(value = "게시글 작성")
    public ApiResponse insertBoard(@RequestParam String boardTitle, @RequestParam String boardContent,
                                   @RequestParam int boardNo, @RequestParam String boardStatus) {

        ApiResponse response = new ApiResponse();

        BoardDto board = new BoardDto();
        board.setBoardContent(boardContent);
        board.setBoardTitle(boardTitle);
        board.setBoardStatus(boardStatus);
        board.setBoardNo(boardNo);

        System.getLogger("왜안돼" + boardTitle + boardNo);

        response = boardService.insertBoard(board);

        return response;
    }

}

