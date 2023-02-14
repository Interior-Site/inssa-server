package com.inssa.server.api.board.controller;


import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.service.BoardService;
import com.inssa.server.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/board")
@RestController
@RequiredArgsConstructor
@Api(tags="게시판")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    @PostMapping(value="/insert") @ApiOperation(value = "게시글 작성")
    public ApiResponse insertBoard(@RequestBody BoardDto board) {

        ApiResponse response = new ApiResponse();

        response = boardService.insertBoard(board);

        return response;
    }

}

