package com.inssa.server.api.board.controller;

import com.inssa.server.api.board.UploadFile;
import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.dto.LikeDto;
import com.inssa.server.api.board.dto.StarDto;
import com.inssa.server.api.board.dto.ZzimDto;
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

@RequestMapping("/api/v1/board/Communication")
@RestController
@RequiredArgsConstructor
@Api(tags = "소통 게시판")
@Slf4j
public class CommuController {

    @Autowired
    private UploadFile uploadFile;




}
