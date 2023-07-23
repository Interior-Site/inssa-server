package com.inssa.server.api.review.build.service;

import com.inssa.server.api.comment.dto.CommentDto;
import com.inssa.server.api.review.build.dao.BuildDao;
import com.inssa.server.api.review.build.dto.BuildDto;
import com.inssa.server.common.ApiResponse;
import com.inssa.server.common.ResponseMessage;
import com.inssa.server.common.StatusCode;
import com.inssa.server.common.code.StatusCode;
import com.inssa.server.common.response.ApiResponse;
import com.inssa.server.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BuildService")
@RequiredArgsConstructor
public class BuildService {

    private final BuildDao builddao;

    public ApiResponse selectList(){
        ApiResponse response = new ApiResponse();
        int statusCode = StatusCode.FAIL;
        String message = ResponseMessage.FAIL;

        List<BuildDto> buildList = builddao.selectList();

        if(!selectList.isEmpty()){
            statusCode = StatusCode.SUCCESS;
            message = "시공후기 게시글 목록 조회 성공" + ResponseMessage.SUCCESS;
        }

        response.setStatusCode(statusCode);
        response.setResponseMessage(message);
        response.putData("result", selectList);
        return response;
    }
}
