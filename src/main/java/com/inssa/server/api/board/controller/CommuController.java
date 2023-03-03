package com.inssa.server.api.board.controller;



import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.service.CommuService;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.FileStore;
import com.inssa.server.common.Files;
import com.inssa.server.common.Pagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import java.util.List;


@RequestMapping("/api/v1/board")
@RestController
@RequiredArgsConstructor
@Api(tags="Board")
@Slf4j
public class CommuController {

    private final CommuService commuService;
//    @Autowired private UploadFile uploadFile;

    private FileStore fileStore;


    @PostMapping(value="/insert") @ApiOperation(value = "게시글 작성")
    public ApiResponse insertBoard(@RequestBody BoardDto board) throws IOException {

        ApiResponse response = new ApiResponse();

        List<Files> imageFiles = fileStore.storeFiles(board.getRequestImg());

        board.setBoardImg(imageFiles);

        response = commuService.insertBoard(board);

        return response;
    }

    @GetMapping(value="/select") @ApiOperation(value="게시글 목록 조회")
    public ApiResponse selectBoardList(@RequestParam int boardNo) {

        ApiResponse response = new ApiResponse();
        response = commuService.selectBoard(boardNo);
        return response;
    }

    @GetMapping(value="/select/{boardNo}") @ApiOperation(value="게시글 상세 조회")
    public ApiResponse selectBoardContent(@PathVariable int boardNo, @RequestParam String userId, HttpServletRequest request) {

        ApiResponse response = new ApiResponse();
        response = commuService.selectBoardNo(boardNo, userId, request);
        return response;
    }

    @GetMapping(value="/delete/{boardNo}") @ApiOperation(value="게시글삭제")
    public ApiResponse deleteBoard(@RequestParam int boardNo, @RequestParam String userId) {

        ApiResponse response = new ApiResponse();
        response = commuService.deleteBoard(boardNo, userId);
        return response;
    }

    @GetMapping(value="/update/{boardNo}") @ApiOperation(value="게시글수정")
    public ApiResponse updateBoard(@RequestParam BoardDto board, MultipartFile files) throws IOException {

        ApiResponse response = new ApiResponse();

//        if(!files.isEmpty()) {
//            String img = uploadFile.fileUpload(files);
//            board.setBoardImg(img);
//        }

        response = commuService.updateBoard(board);
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
            paging = commuService.getPaging(dto, paging);
            response = commuService.searchBoardList(dto);
        } else {

        }
        return response;

    }

}

